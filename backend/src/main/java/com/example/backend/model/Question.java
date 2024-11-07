package com.example.backend.model;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Builder
@Document(collection = "question")
public record Question (String id, String courseId, String text, List<String> options, String correctAnswer) {
}
