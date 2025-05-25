package com.helmesbackend.task.helmes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private Map<String, String> errors;

    public static ErrorResponseDTO of(int status, String message) {
        return ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .message(message)
                .errors(new HashMap<>())
                .build();
    }

    public void addError(String field, String errorMessage) {
        if (errors == null) {
            errors = new HashMap<>();
        }
        errors.put(field, errorMessage);
    }
}
