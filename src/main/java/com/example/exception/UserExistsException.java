package com.example.exception;

public class UserExistsException extends RuntimeException{
    public UserExistsException() 
    {

    }

    public String getMessage(){
        return "Username already exists. Choose another.";
    }
}
