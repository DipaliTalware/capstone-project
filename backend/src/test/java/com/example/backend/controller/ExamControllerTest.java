package com.example.backend.controller;

import com.example.backend.model.ExamQuestion;
import com.example.backend.services.ExamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ExamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamService examService;

    private List<ExamQuestion> sampleQuestions;

    @BeforeEach
    public void setUp() {
        sampleQuestions = Arrays.asList(
                new ExamQuestion("1", "Question 1", Arrays.asList("Option A", "Option B", "Option C", "Option D"), Arrays.asList("Option A")),
                new ExamQuestion("2", "Question 2", Arrays.asList("Option A", "Option B", "Option C", "Option D"), Arrays.asList("Option B"))
        );
    }

    @Test
    public void testAddQuestions() throws Exception {
        String questionsJson = "[{\"id\":\"1\",\"question\":\"Question 1\",\"options\":[\"Option A\",\"Option B\",\"Option C\",\"Option D\"],\"correctAnswers\":[\"Option A\"]}," +
                "{\"id\":\"2\",\"question\":\"Question 2\",\"options\":[\"Option A\",\"Option B\",\"Option C\",\"Option D\"],\"correctAnswers\":[\"Option B\"]}]";

        mockMvc.perform(post("/exam/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(questionsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Questions added successfully"));
    }

    @Test
    public void testGetAllQuestions() throws Exception {
        Mockito.when(examService.getAllQuestions()).thenReturn(sampleQuestions);

        mockMvc.perform(get("/exam/questions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].question").value("Question 1"))
                .andExpect(jsonPath("$[1].question").value("Question 2"));
    }
}