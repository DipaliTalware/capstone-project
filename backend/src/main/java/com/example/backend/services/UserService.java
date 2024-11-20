package com.example.backend.services;

import com.example.backend.dto.SignupRequest;
import com.example.backend.model.UserModel;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Method to validate password format (at least one uppercase, one number, and min length of 8)
    public boolean isPasswordValid(String password) {
        return password.matches(".*[A-Z].*") &&    // At least one uppercase letter
                password.matches(".*[a-z].*") &&    // At least one lowercase letter
                password.matches(".*[0-9].*") &&    // At least one number
                password.length() >= 8;             // Minimum 8 characters
    }

    // Method to register a new user
    public UserModel registerUser(SignupRequest signupRequest) {
        if (!isPasswordValid(signupRequest.getPassword())) {
            throw new IllegalArgumentException("Password does not meet the criteria");
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        UserModel user = UserModel.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(encodedPassword)
                .build();

        return userRepository.save(user);
    }

    // Method to find a user by email
    public UserModel findByEmail(String email) {
        Optional<UserModel> userOptional = userRepository.findByEmail(email);
        return userOptional.orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    public UserModel findByUserName(String username) {
        Optional<UserModel> userOptional = userRepository.findByUsername(username);
        return userOptional.orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
    }

    // Method to verify if the provided password matches the stored password
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        System.out.println("rawPassword: " + rawPassword);
        System.out.println("encodedPassword: " + encodedPassword);
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
