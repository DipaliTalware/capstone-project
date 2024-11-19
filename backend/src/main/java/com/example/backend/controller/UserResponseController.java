package com.example.backend.controller;

import com.example.backend.model.UserResponse;
import com.example.backend.services.UserResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-responses")
public class UserResponseController {

    @Autowired
    private UserResponseService userResponseService;

    @PostMapping
    public ResponseEntity<UserResponse> createUserResponse(@RequestBody UserResponse userResponse) {
        UserResponse createdResponse = userResponseService.createUserResponse(userResponse);
        return ResponseEntity.ok(createdResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserResponse(@PathVariable String id, @RequestBody UserResponse userResponse) {
        UserResponse updatedResponse = userResponseService.updateUserResponse(id, userResponse);
        return ResponseEntity.ok(updatedResponse);
    }
}