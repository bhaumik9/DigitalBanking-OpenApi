package com.casestudy.digitalbankingopenapi.exception;

public class InvalidLanguageException extends RuntimeException{
    public InvalidLanguageException() {
        super("Invalid Preferred Language");
    }
}
