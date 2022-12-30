package com.example.demo.service;

import com.example.demo.dto.MessageDto;
import com.example.demo.entity.Message;
import com.example.demo.repository.MessageRepository;
import org.flywaydb.core.internal.jdbc.JdbcUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private MessageRepository repository;

    public MessageService (MessageRepository repository) {
        this.repository = repository;
    }

    @Value("${spring.datasource.url}")
    String datasourceUrl;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    public ResponseEntity<?> sendMessage(MessageDto messageDto) throws Exception {

        String text = messageDto.getMessageText();
        String name = messageDto.getName();
        if (text.startsWith("history ")) {
            String [] arr = text.split(" ");
            int limit = Integer.parseInt(arr[1]);
            List<String> list = executeMessageQuery(name,limit);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            Message message = new Message();
            message.setName(name);
            message.setMessageText(text);
            repository.save(message);
            return new ResponseEntity<>("That message had been saved", HttpStatus.OK);
        }
    }

    private List<String> executeMessageQuery (String name, int limit) throws SQLException {
        List<String> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        String sqlName = "'" + name +"'";
        String query = "SELECT messageText FROM messages WHERE name = " + sqlName + " limit " + limit;
        try {
            connection = DriverManager.getConnection(datasourceUrl, username, password);
            statement = connection.prepareStatement(query);
            ResultSet set = statement.executeQuery(query);
            while (set.next()){
                String message = set.getString("messageText");
                list.add(message);
            }
            statement.close();
            connection.close();
        } catch (SQLException E) {
            System.out.println("Problem with SQL");
        }
        return list;
    }
}
