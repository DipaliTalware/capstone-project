package com.example.backend.controller;

import com.example.backend.model.Question;
import com.example.backend.repository.QuestionRepository;
import com.example.backend.services.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QuestionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;

    @Test
    public void getAllQuestions_ShouldReturnListOfQuestions() throws Exception {
        // Arrange
        questionRepository.saveAll(List.of(
                new Question("1", "101", "What is Java?", List.of("Programming Language", "Coffee"), "Programming Language"),
                new Question("2", "102", "What is Spring?", List.of("Framework", "Season"), "Framework")
        ));

        // Act & Assert
        mockMvc.perform(get("/questions-answers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").value("What is Java?"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].text").value("What is Spring?"));
    }

    @Test
    public void createQuestions_ShouldSaveAndReturnListOfQuestions() throws Exception {
        // Arrange
        String newQuestionsJson = "[{\"id\":\"1\",\"code\":\"101\",\"text\":\"What is Java?\",\"options\":[\"Programming Language\",\"Coffee\"],\"correctAnswer\":\"Programming Language\"}," +
                "{\"id\":\"2\",\"code\":\"102\",\"text\":\"What is Spring?\",\"options\":[\"Framework\",\"Season\"],\"correctAnswer\":\"Framework\"}]";

        // Act & Assert
        mockMvc.perform(post("/questions-answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newQuestionsJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").value("What is Java?"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].text").value("What is Spring?"));
    }
}