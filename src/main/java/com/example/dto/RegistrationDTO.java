package com.example.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RegistrationDTO(
        @NotNull
        @NotEmpty
        String name,
        @NotNull
        @NotEmpty
        String surname,
        @NotNull
        @NotEmpty
        String email,
        @NotNull
        @NotEmpty
        String password
) {
}
