package com.casestudy.digitalbankingopenapi.exception;

public class OtpExpiredException extends RuntimeException{
    public OtpExpiredException(String message) {
        super(message);
    }
}
