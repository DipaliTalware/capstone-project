package com.example.backend.controller;

import com.example.backend.model.UserModel;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserModel registerUser(@RequestBody UserModel userModel) {
        return userService.registerUser(userModel);
    }

    @GetMapping("/{username}")
    public Optional<UserModel> getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }
}
