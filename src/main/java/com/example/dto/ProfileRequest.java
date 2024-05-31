package com.example.dto;

public record ProfileRequest(
        String id,
        String name,
        String surname,
        String email,
        String password
) {
}
