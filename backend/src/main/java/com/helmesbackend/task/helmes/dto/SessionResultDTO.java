package com.helmesbackend.task.helmes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionResultDTO {
    private PersonDTO person;
    private String token;
    private boolean newSession;
}
