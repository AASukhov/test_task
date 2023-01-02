package com.example.demo;

import com.example.demo.controller.MessageController;
import com.example.demo.dto.UserDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    private static final String RIGHT_USER_NAME = "user1";
    private static final String WRONG_USER_NAME = "wrong_name";
    private static final String PASSWORD = "qwerty";

    private static final String ENDPOINT_LOGIN = "/login";
    private static final String ENDPOINT_LOGOUT = "/logout";

    private static UserDto valid;
    private static UserDto invalid;

    private static final Gson gson = new Gson();

    @BeforeAll
    public static void beforeAll(){
        valid = new UserDto(RIGHT_USER_NAME, PASSWORD);
        invalid = new UserDto(WRONG_USER_NAME, PASSWORD);
    }

    @Test
    void testLoginSuccess() throws Exception {
        mockMvc.perform(post(ENDPOINT_LOGIN).contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(valid))).andExpect(status().isOk());
    }

    @Test
    void testLoginFail() throws Exception {
        mockMvc.perform(post(ENDPOINT_LOGIN).contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(invalid))).andExpect(status().isUnauthorized());
    }

    @Test
    void testLogoutSuccess() throws Exception {
        mockMvc.perform(post(ENDPOINT_LOGOUT).contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(valid))).andExpect(status().isOk());
    }

    @Test
    void FilesControllerTest() {
        ServletContext servletContext = context.getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertNotNull(context.getBean(MessageController.class));
    }
}
