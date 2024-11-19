package com.example.backend.controller;

import com.example.backend.model.UserModel;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public String signup(@RequestBody UserModel userModel) {
        userModel = new UserModel(
                userModel.id(),
                userModel.username(),
                userModel.email(),
                passwordEncoder.encode(userModel.password())
        );
        userRepository.save(userModel);
        return "User registered successfully";
    }
}