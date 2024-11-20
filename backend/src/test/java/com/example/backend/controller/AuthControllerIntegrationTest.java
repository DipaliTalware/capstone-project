package com.example.backend.controller;

import com.example.backend.model.UserModel;
import com.example.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testSignup() throws Exception {
        String userJson = "{\"username\":\"testuser\",\"email\":\"test@example.com\",\"password\":\"Password123#\"}";

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testSignup_invalidPassword() throws Exception {
        String userJson = "{\"username\":\"testuser\",\"email\":\"test@example.com\",\"password\":\"password\"}";

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testLogin() throws Exception {
        // First, create a user
        UserModel user = new UserModel("testuser", "testuser", "test@example.com", passwordEncoder.encode("password"));
        userRepository.save(user);

        // Now, attempt to login
        String loginJson = "{\"username\":\"testuser\",\"password\":\"password\"}";

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }

    @Test
    public void testLoginWithInvalidPassword() throws Exception {
        // First, create a user
        UserModel user = new UserModel("testuser", "testuser", "test@example.com", passwordEncoder.encode("password"));
        userRepository.save(user);

        // Now, attempt to login with an invalid password
        String loginJson = "{\"username\":\"testuser\",\"password\":\"wrongpassword\"}";

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isUnauthorized());
    }
}