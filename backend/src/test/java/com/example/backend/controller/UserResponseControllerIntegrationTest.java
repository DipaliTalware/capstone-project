package com.example.backend.controller;

import com.example.backend.model.UserResponse;
import com.example.backend.repository.UserResponseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserResponseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserResponseRepository userResponseRepository;

    @Test
    public void testCreateUserResponse() throws Exception {
        String userResponseJson = "{\"userId\":\"user1\",\"questionId\":\"question1\",\"selectedAnswers\":[\"answer1\",\"answer2\"]}";

        mockMvc.perform(post("/user-responses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userResponseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user1"))
                .andExpect(jsonPath("$.questionId").value("question1"))
                .andExpect(jsonPath("$.selectedAnswers[0]").value("answer1"))
                .andExpect(jsonPath("$.selectedAnswers[1]").value("answer2"));
    }

    @Test
    public void testUpdateUserResponse() throws Exception {
        UserResponse existingResponse = new UserResponse();
        existingResponse.setUserId("user1");
        existingResponse.setQuestionId("question1");
        existingResponse.setSelectedAnswers(Arrays.asList("answer1"));
        userResponseRepository.save(existingResponse);

        String updatedResponseJson = "{\"userId\":\"user2\",\"questionId\":\"question2\",\"selectedAnswers\":[\"answer2\",\"answer3\"]}";

        mockMvc.perform(put("/user-responses/" + existingResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedResponseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user2"))
                .andExpect(jsonPath("$.questionId").value("question2"))
                .andExpect(jsonPath("$.selectedAnswers[0]").value("answer2"))
                .andExpect(jsonPath("$.selectedAnswers[1]").value("answer3"));
    }
}