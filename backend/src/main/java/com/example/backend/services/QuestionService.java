package com.example.backend.services;

import com.example.backend.dto.QuestionDTO;
import com.example.backend.model.Question;
import com.example.backend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionDTO> getAllQuestions() {
        List<Question> allQuestions = questionRepository.findAll();
        return allQuestions.stream().map(question ->
                        new QuestionDTO(question.id(),
                                question.courseId(),
                                question.text(),
                                question.options(),
                                question.correctAnswer()))
                .toList();
    }


    public List<QuestionDTO> createQuestions(List<QuestionDTO> questionDTOs) {

        List<Question> questions = questionDTOs.stream()
                .map(questionDTO -> {
                    return new Question(
                            null,
                            questionDTO.courseId(),
                            questionDTO.text(),
                            questionDTO.options(),
                            questionDTO.correctAnswer()
                    );
                })
                .collect(Collectors.toList());


        List<Question> savedQuestions = questionRepository.saveAll(questions);


        return savedQuestions.stream()
                .map(savedQuestion -> new QuestionDTO(
                        savedQuestion.id(),
                        savedQuestion.courseId(),
                        savedQuestion.text(),
                        savedQuestion.options(),
                        savedQuestion.correctAnswer()
                ))
                .collect(Collectors.toList());

    }
}

