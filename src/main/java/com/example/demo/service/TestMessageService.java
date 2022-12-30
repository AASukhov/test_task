package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestMessageService {


    static String datasourceUrl = "jdbc:mysql://localhost:3306/test_task";

    static String username = "root";

    static String password = "49918003";

    public static void main(String[] args) throws SQLException{
        System.out.println(executeMessageQuery("'user1'", 10));
    }

    private static List<String> executeMessageQuery (String name, int limit) throws SQLException {
        List<String> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "SELECT messageText FROM messages WHERE name = " + name + " LIMIT " + limit;

        try {
            connection = DriverManager.getConnection(datasourceUrl, username, password);
            System.out.println("connection is on");
            statement = connection.prepareStatement(query);
            System.out.println(statement);
            ResultSet set = statement.executeQuery(query);
            System.out.println(set);
            while (set.next()){
                String message = set.getString("messageText");
                list.add(message);
            }
            statement.close();
            connection.close();
        } catch (SQLException E) {
            System.out.println("shit");
        }
        return list;
    }
}
