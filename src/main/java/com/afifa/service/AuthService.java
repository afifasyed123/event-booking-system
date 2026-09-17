package com.afifa.service;

import com.afifa.dto.LoginRequestDTO;
import com.afifa.exception.UserNotFoundException;
import com.afifa.model.User;
import com.afifa.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager manager;
    private final UserRepository repository;
    private final JwtService jwtService;
    public AuthService(AuthenticationManager manager, UserRepository repository, JwtService jwtService){
        this.manager=manager;
        this.repository = repository;
        this.jwtService = jwtService;
    }
    public String login(LoginRequestDTO request){
       UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());

       manager.authenticate(token);
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UserNotFoundException("User not found")
                );
        return jwtService.generateToken(user);
    }
}
