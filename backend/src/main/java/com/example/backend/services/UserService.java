package com.example.backend.services;

import com.example.backend.model.UserModel;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel registerUser(UserModel userModel) {
        if (!isPasswordValid(userModel.password())) {
            throw new IllegalArgumentException("Password does not meet the required criteria");
        }
        return userRepository.save(userModel);
    }

    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private boolean isPasswordValid(String password) {
        return isPasswordLengthValid(password) && containsUpperCase(password) && containsLowerCase(password) && containsDigit(password) && containsSpecialCharacter(password);
    }

    private boolean isPasswordLengthValid(String password) {
        return password.length() >= 8;
    }

    private boolean containsUpperCase(String password) {
        return password.chars().anyMatch(Character::isUpperCase);
    }

    private boolean containsLowerCase(String password) {
        return password.chars().anyMatch(Character::isLowerCase);
    }

    private boolean containsDigit(String password) {
        return password.chars().anyMatch(Character::isDigit);
    }

    private boolean containsSpecialCharacter(String password) {
        return password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
    }
}