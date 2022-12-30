package com.example.demo.controller;

import com.example.demo.dto.AuthenticationResponseDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class AuthController {

    private AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponseDto> login (@RequestBody UserDto userDto){
        return service.login(userDto);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto){
        return service.register(userDto);
    }
}
