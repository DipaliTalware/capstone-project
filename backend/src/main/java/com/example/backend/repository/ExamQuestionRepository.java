package com.example.backend.repository;

import com.example.backend.model.ExamQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExamQuestionRepository extends MongoRepository<ExamQuestion, String> {
}