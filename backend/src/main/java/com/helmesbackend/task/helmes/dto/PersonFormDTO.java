package com.helmesbackend.task.helmes.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonFormDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotEmpty(message = "At least one sector must be selected")
    private Set<Long> sectorIds;

    @NotNull(message = "Terms agreement is required")
    @AssertTrue(message = "You must agree to the terms")
    private Boolean agreeTerms;
}
