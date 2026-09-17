package com.afifa.service;

import com.afifa.dto.UserDTO;
import com.afifa.exception.UserNotFoundException;
import com.afifa.model.User;
import com.afifa.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder){
        this.repository=repository;
        this.passwordEncoder = passwordEncoder;
    }
    public UserDTO createUser(User user) {
        user.setRole("USER");
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        repository.save(user);
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }

    public List<User> getAllUsers(){
        return repository.findAll();
}
public User updateUser(Long id,User updatedUser){
        User user = repository.findById(id).orElseThrow(
                ()-> new UserNotFoundException("User not found")
        );
        user.setEmail(updatedUser.getEmail());
        user.setId(updatedUser.getId());
        user.setBookings(updatedUser.getBookings());
        user.setName(updatedUser.getName());
        user.setPassword(updatedUser.getPassword());
        return repository.save(updatedUser);
}
    public void deleteUser(Long id) {
        User user = repository.findById(id).orElseThrow(
                ()-> new UserNotFoundException("User not found")
        );
        repository.delete(user);
    }
    public UserDTO getUserDTO(Long id){
        User user=repository.findById(id).orElseThrow(
                ()->new UserNotFoundException("User not found")
        );
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }
}
