package com.example.backend.services;

import com.example.backend.model.UserResponse;
import com.example.backend.repository.UserResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserResponseService {

    @Autowired
    private UserResponseRepository userResponseRepository;

    public UserResponse createUserResponse(UserResponse userResponse) {
        return userResponseRepository.save(userResponse);
    }

    public UserResponse updateUserResponse(String id, UserResponse userResponse) {
        Optional<UserResponse> existingResponse = userResponseRepository.findById(id);
        if (existingResponse.isPresent()) {
            UserResponse responseToUpdate = existingResponse.get();
            responseToUpdate.setUserId(userResponse.getUserId());
            responseToUpdate.setQuestionId(userResponse.getQuestionId());
            responseToUpdate.setSelectedAnswers(userResponse.getSelectedAnswers());
            return userResponseRepository.save(responseToUpdate);
        } else {
            throw new IllegalArgumentException("UserResponse with id " + id + " not found");
        }
    }
}