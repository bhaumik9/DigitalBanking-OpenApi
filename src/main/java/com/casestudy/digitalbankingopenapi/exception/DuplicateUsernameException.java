package com.casestudy.digitalbankingopenapi.exception;

public class DuplicateUsernameException extends RuntimeException{
    public DuplicateUsernameException() {
        super("Duplicate Username Found");
    }
}
