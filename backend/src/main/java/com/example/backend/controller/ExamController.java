package com.example.backend.controller;

import com.example.backend.model.ExamQuestion;
import com.example.backend.services.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @PostMapping("/questions")
    public ResponseEntity<?> addQuestions(@RequestBody @Valid List<ExamQuestion> questions) {
        if (questions == null || questions.isEmpty()) {
            return ResponseEntity.badRequest().body("No questions provided");
        }
        examService.saveAllQuestions(questions);
        return ResponseEntity.ok("Questions added successfully");
    }

    @GetMapping("/questions")
    public ResponseEntity<List<ExamQuestion>> getAllQuestions() {
        List<ExamQuestion> questions = examService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }
}