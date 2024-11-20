package com.example.backend.controller;

import com.example.backend.dto.SignupRequest;
import com.example.backend.model.UserModel;
import com.example.backend.security.JwtTokenProvider;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider; // JwtTokenProvider for generating the token

    @Autowired
    private AuthenticationManager authenticationManager; // For authenticating the user

    @PostMapping("/signup")
    public ResponseEntity<UserModel> signup(@RequestBody SignupRequest signupRequest) {
        try {
            UserModel user = userService.registerUser(signupRequest);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody SignupRequest loginRequest) {
        UserModel user;

        if( null != loginRequest.getUsername())
            user = userService.findByUserName(loginRequest.getUsername());
        else
            user = userService.findByEmail(loginRequest.getEmail());

        if (user != null && userService.verifyPassword(loginRequest.getPassword(), user.password())) {
            System.out.println("Password is correct");
            // Authenticate the user using Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.email(), loginRequest.getPassword())
            );

            System.out.println("Authentication: is done" + authentication);

            // If authentication is successful, generate JWT
            String token = jwtTokenProvider.generateJwtToken(authentication);
            return ResponseEntity.ok(Collections.singletonMap("accessToken", token)); // Sending the token back to the frontend
        } else {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Invalid credentials"));
        }
    }
}
