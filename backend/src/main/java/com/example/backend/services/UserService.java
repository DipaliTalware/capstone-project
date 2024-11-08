package com.example.backend.services;

import com.example.backend.model.UserModel;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel registerUser(UserModel userModel) {
        return userRepository.save(userModel);
    }

    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
