package com.example.demo;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserDetailsServiceTest {
    @InjectMocks
    private CustomUserDetailsService service;

    @Mock
    private UserRepository userRepository;

    public static final String USERNAME = "user1";
    public static final String PASSWORD = "qwerty";
    public static final com.example.demo.entity.User USER = new com.example.demo.entity.User(1, USERNAME, PASSWORD);

    public static final UserDetails USER_DETAILS = User.builder()
            .username(USERNAME)
            .password(PASSWORD)
            .authorities(new ArrayList<>())
            .build();

    @BeforeEach
    void setUp() {
        Mockito.when(userRepository.findUserByName(USERNAME)).thenReturn(USER);
    }

    @Test
    void loadUserByUsername() {
        assertEquals(USER_DETAILS, service.loadUserByUsername(USERNAME));
    }
}
