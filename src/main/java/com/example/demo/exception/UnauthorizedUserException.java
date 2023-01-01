package com.example.demo.exception;

public class UnauthorizedUserException extends Exception{
    public UnauthorizedUserException (String message) {
        super(message);
    }
}
