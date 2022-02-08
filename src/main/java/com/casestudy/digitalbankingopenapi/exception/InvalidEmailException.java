package com.casestudy.digitalbankingopenapi.exception;

public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException() {
        super("Invalid Email Address");
    }
}
