package com.example.demoApp.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String msg){
        super(msg); //This sends the message to the parent class (RuntimeException).
    }
    
}
