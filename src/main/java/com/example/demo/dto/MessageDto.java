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
    private String text;

    public MessageDto (String name, String text) {
        this.name = name;
        this.text = text;
    }
}
