package com.example.backend.repository;

import com.example.backend.model.UserResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface UserResponseRepository extends MongoRepository<UserResponse, String> {
    List<UserResponse> findAllByUserId(String userId);
}