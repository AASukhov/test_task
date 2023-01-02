package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthenticationResponseDto {
    @JsonProperty("token")
    private String token;

    public AuthenticationResponseDto(String token) {
        this.token = token;
    }
}
