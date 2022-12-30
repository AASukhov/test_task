package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public AppRunner (UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.save(User.builder()
                .name("user1")
                .password(encoder.encode("qwerty"))
                .build());
        repository.save(User.builder()
                .name("user2")
                .password(encoder.encode("wasd"))
                .build());
        repository.save(User.builder()
                .name("user3")
                .password(encoder.encode("dasdas"))
                .build());

    }
}
