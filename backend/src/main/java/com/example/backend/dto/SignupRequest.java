package com.example.backend.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequest {
    private String username;
    private String email;
    private String password;

}
