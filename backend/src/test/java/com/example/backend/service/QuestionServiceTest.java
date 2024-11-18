package com.example.backend.service;

import com.example.backend.dto.QuestionDTO;
import com.example.backend.model.Question;
import com.example.backend.repository.QuestionRepository;
import com.example.backend.services.QuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository = mock(QuestionRepository.class);

    @InjectMocks
    private QuestionService questionService;


    @Test
    void getAllQuestions_ShouldReturnListOfQuestionDTO() {

        List<Question> questions = Arrays.asList(
                new Question("1", "101", "What is Java?", List.of("Programming Language", "Coffee"), "Programming Language"),
                new Question("2", "102", "What is Spring?", List.of("Framework", "Season"), "Framework")
        );

        when(questionRepository.findAll()).thenReturn(questions);


        List<QuestionDTO> result = questionService.getAllQuestions();


        assertEquals(2, result.size());
        assertEquals("What is Java?", result.get(0).text());
        assertEquals("What is Spring?", result.get(1).text());
    }


    @Test
    void createQuestions_ShouldSaveAndReturnListOfQuestionDTO() {

        List<QuestionDTO> questionDTOs = Arrays.asList(
                new QuestionDTO("1", "101", "What is Java?", List.of("Programming Language", "Coffee"), "Programming Language"),
                new QuestionDTO("2", "102", "What is Spring?", List.of("Framework", "Season"), "Framework")
        );

        List<Question> savedQuestions = Arrays.asList(
                new Question("1", "101", "What is Java?", List.of("Programming Language", "Coffee"), "Programming Language"),
                new Question("2", "102", "What is Spring?", List.of("Framework", "Season"), "Framework")
        );


        when(questionRepository.saveAll(anyList())).thenReturn(savedQuestions);


        List<QuestionDTO> result = questionService.createQuestions(questionDTOs);


        assertEquals(2, result.size());
        assertEquals("What is Java?", result.get(0).text());
        assertEquals("1", result.get(0).id());
        assertEquals("What is Spring?", result.get(1).text());
        assertEquals("2", result.get(1).id());
    }


}
