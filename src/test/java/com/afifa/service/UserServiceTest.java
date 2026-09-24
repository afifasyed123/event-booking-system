package com.afifa.service;

import com.afifa.dto.EventResponseDTO;
import com.afifa.dto.UserDTO;
import com.afifa.exception.EventNotFoundException;
import com.afifa.exception.UserNotFoundException;
import com.afifa.model.Event;
import com.afifa.model.User;
import com.afifa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Test
    void getUserById_shouldReturnUser() {

        User user = new User();
        user.setId(1L);
        user.setName("Afifa");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        UserDTO result = userService.getUserDTO(1L);

        assertEquals("Afifa", result.getName());
    }
    @Test

    void getUserDTO_shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserDTO(1L);
        });

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void createUser_shouldCreateUser() {

        User user = new User();
        user.setId(1L);
        user.setName("Afifa");
        user.setEmail("afifa@gmail.com");
        user.setPassword("123456");

        when(passwordEncoder.encode("123456"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        UserDTO result = userService.createUser(user);

        assertEquals("Afifa", result.getName());
        assertEquals("afifa@gmail.com", result.getEmail());

        verify(passwordEncoder, times(1))
                .encode("123456");

        verify(userRepository, times(1))
                .save(user);
    }
}