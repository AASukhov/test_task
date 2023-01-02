package com.example.demo;

import com.example.demo.controller.AuthController;
import com.example.demo.dto.MessageDto;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.TokenGenerator;
import com.example.demo.service.MessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MessageServiceTest {

    @InjectMocks
    private MessageService service;

    @Mock
    private TokenGenerator generator;

    @Mock
    private UserRepository repository;

    @Mock
    private MessageRepository messageRepository;

    public static final String USERNAME = "user1";
    public static final String PASSWORD = "qwerty";
    public static final User user = new User(1, USERNAME,PASSWORD);
    public static final String AUTH_TOKEN = "token";
    public static final String MESSAGE_1 = "message1";
    public static final String MESSAGE_2 = "history 1";
    public static final MessageDto messageDto1 = new MessageDto(USERNAME,MESSAGE_1);
    public static final MessageDto messageDto2 = new MessageDto(USERNAME, MESSAGE_2);



    @BeforeEach
    void setUp() {
        Mockito.when(generator.getNameFromToken(AUTH_TOKEN)).thenReturn(USERNAME);
        Mockito.when(repository.findUserByName(USERNAME)).thenReturn(user);
    }

    @Test
    void sendMessage1() throws Exception {
        assertEquals(new ResponseEntity<>(
                "That message had been saved", HttpStatus.OK),
                service.sendMessage(messageDto1, AUTH_TOKEN));
    }
}
