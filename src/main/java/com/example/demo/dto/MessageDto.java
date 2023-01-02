package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MessageDto {
    private String name;
    private String message;

    public MessageDto (String name, String message) {
        this.name = name;
        this.message = message;
    }
}
