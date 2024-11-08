package com.example.backend.controller;

import com.example.backend.dto.QuestionDTO;
import com.example.backend.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:5174")
@RequestMapping("/questions-answers")

public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<QuestionDTO> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @PostMapping
    public List<QuestionDTO> createQuestions(@RequestBody List<QuestionDTO> questionDTOs) {
        return questionService.createQuestions(questionDTOs);
    }
}

