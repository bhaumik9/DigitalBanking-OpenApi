package com.casestudy.digitalbankingopenapi.exception;

public class InvalidUsernameException extends RuntimeException{
    public InvalidUsernameException() {
        super("Invalid username");
    }
}
