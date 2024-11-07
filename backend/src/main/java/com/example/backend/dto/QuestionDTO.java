package com.example.backend.dto;

import java.util.List;
    public record QuestionDTO(
            String id,
            String courseId,
            String text,
            List<String> options,
            String correctAnswer
    ) {}



