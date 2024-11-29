package com.example.backend.services;

import com.example.backend.model.ExamQuestion;
import com.example.backend.repository.ExamQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    private final ExamQuestionRepository examQuestionRepository;

    public ExamService(ExamQuestionRepository examQuestionRepository) {
        this.examQuestionRepository = examQuestionRepository;
    }

    public void saveAllQuestions(List<ExamQuestion> questions) {
        if (questions == null || questions.isEmpty()) {
            throw new IllegalArgumentException("Question list cannot be empty");
        }
        examQuestionRepository.saveAll(questions);
    }

    public List<ExamQuestion> getAllQuestions() {
        return examQuestionRepository.findAll();
    }
}