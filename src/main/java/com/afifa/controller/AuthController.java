package com.afifa.controller;

import com.afifa.dto.LoginRequestDTO;
import com.afifa.exception.UserNotFoundException;
import com.afifa.model.User;
import com.afifa.repository.UserRepository;
import com.afifa.service.AuthService;
import com.afifa.service.JwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service){
        this.service=service;

    }
    @PostMapping("/login")
    public String login(@RequestBody  LoginRequestDTO request){

        return service.login(request);
    }


}
