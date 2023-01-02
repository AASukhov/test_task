package com.example.demo.service;

import com.example.demo.dto.MessageDto;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.exception.UnauthorizedUserException;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private TokenGenerator generator;
    private UserRepository userRepository;

    public MessageService (MessageRepository messageRepository,
                           TokenGenerator generator,
                           UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.generator = generator;
        this.userRepository = userRepository;
    }

    @Value("${spring.datasource.url}")
    String datasourceUrl;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    public ResponseEntity<?> sendMessage(MessageDto messageDto, String token) throws Exception {
        final User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        String message = messageDto.getMessage();
        String name = messageDto.getName();
        if (message.startsWith("history ")) {
            String [] arr = message.split(" ");
            int limit = Integer.parseInt(arr[1]);
            List<String> list = executeMessageQuery(name,limit);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            Message text = new Message();
            text.setName(name);
            text.setMessage(message);
            messageRepository.save(text);
            return new ResponseEntity<>("That message had been saved", HttpStatus.OK);
        }
    }

    private List<String> executeMessageQuery (String name, int limit) throws SQLException {
        List<String> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        String sqlName = "'" + name +"'";
        String query = "SELECT message FROM messages WHERE name = " + sqlName + " limit " + limit;
        System.out.println(query);
        try {
            System.out.println("testing connection");
            connection = DriverManager.getConnection(datasourceUrl, username, password);
            statement = connection.prepareStatement(query);
            ResultSet set = statement.executeQuery(query);
            while (set.next()){
                String message = set.getString("message");
                list.add(message);
            }
            statement.close();
            connection.close();
        } catch (SQLException E) {
            System.out.println("Problem with SQL");
        }
        return list;
    }

    private User getUser(String authToken) {
        if (authToken.startsWith("Bearer")) {
            authToken = authToken.substring(7);
        }
        final String name = generator.getNameFromToken(authToken);
        return userRepository.findUserByName(name);
    }
}
