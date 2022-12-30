package com.example.demo.controller;

import com.example.demo.dto.MessageDto;
import com.example.demo.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    private MessageService service;

    public MessageController (MessageService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<?> sendMessage(@RequestBody MessageDto messageDto,
                                         @RequestHeader("auth-token") String token) throws Exception {
        return service.sendMessage(messageDto);
    }
}
