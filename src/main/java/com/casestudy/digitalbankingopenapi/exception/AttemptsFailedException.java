package com.casestudy.digitalbankingopenapi.exception;

public class AttemptsFailedException extends RuntimeException {
    public AttemptsFailedException() {
    }

    public AttemptsFailedException(String message) {
        super(message);
    }
}
