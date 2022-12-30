package com.example.demo.service;

import com.example.demo.dto.AuthenticationResponseDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private AuthenticationManager manager;
    private UserRepository repository;
    private TokenGenerator generator;
    private PasswordEncoder encoder;

    @Autowired
    public AuthService(AuthenticationManager manager,
                       UserRepository repository,
                       TokenGenerator generator,
                       PasswordEncoder encoder) {
        this.manager = manager;
        this.repository = repository;
        this.generator = generator;
        this.encoder = encoder;
    }

    public ResponseEntity<AuthenticationResponseDto> login(UserDto userDto){
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(
                userDto.getName(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = generator.generateToken(authentication);

        AuthenticationResponseDto responseDto = new AuthenticationResponseDto(token);
        if (responseDto.getToken()==null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(responseDto);
    }

    public ResponseEntity<String> register(UserDto userDto){
        if (repository.existsByName(userDto.getName())) {
            return new ResponseEntity<>("That login is in use",HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(encoder.encode(userDto.getPassword()));
        repository.save(user);
        return new ResponseEntity<>("User " + userDto.getName() + " has been successfully registered", HttpStatus.OK);
    }
}
