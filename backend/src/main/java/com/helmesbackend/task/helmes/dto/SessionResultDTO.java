package com.helmesbackend.task.helmes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionResultDTO {
    private PersonDTO person;
    private String token;
    private boolean newSession;
}
