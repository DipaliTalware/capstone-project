package com.example.backend.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "users")
public record UserModel(@Id String id, String username, String email, String password) {
}
