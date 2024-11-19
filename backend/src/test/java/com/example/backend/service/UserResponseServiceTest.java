package com.example.backend.services;

import com.example.backend.model.UserResponse;
import com.example.backend.repository.UserResponseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserResponseServiceTest {

    @Mock
    private UserResponseRepository userResponseRepository;

    @InjectMocks
    private UserResponseService userResponseService;

    @Test
    public void testCreateUserResponse() {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId("user1");
        userResponse.setQuestionId("question1");
        userResponse.setSelectedAnswers(Arrays.asList("answer1", "answer2"));

        when(userResponseRepository.save(userResponse)).thenReturn(userResponse);

        UserResponse createdResponse = userResponseService.createUserResponse(userResponse);

        assertNotNull(createdResponse);
        assertEquals("user1", createdResponse.getUserId());
        verify(userResponseRepository, times(1)).save(userResponse);
    }

    @Test
    public void testUpdateUserResponse() {
        UserResponse existingResponse = new UserResponse();
        existingResponse.setId("1");
        existingResponse.setUserId("user1");
        existingResponse.setQuestionId("question1");
        existingResponse.setSelectedAnswers(Arrays.asList("answer1"));

        UserResponse updatedResponse = new UserResponse();
        updatedResponse.setUserId("user2");
        updatedResponse.setQuestionId("question2");
        updatedResponse.setSelectedAnswers(Arrays.asList("answer2", "answer3"));

        when(userResponseRepository.findById("1")).thenReturn(Optional.of(existingResponse));
        when(userResponseRepository.save(existingResponse)).thenReturn(existingResponse);

        UserResponse result = userResponseService.updateUserResponse("1", updatedResponse);

        assertNotNull(result);
        assertEquals("user2", result.getUserId());
        assertEquals("question2", result.getQuestionId());
        assertEquals(Arrays.asList("answer2", "answer3"), result.getSelectedAnswers());
        verify(userResponseRepository, times(1)).findById("1");
        verify(userResponseRepository, times(1)).save(existingResponse);
    }
}