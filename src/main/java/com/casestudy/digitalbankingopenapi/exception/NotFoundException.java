package com.casestudy.digitalbankingopenapi.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    String type;

    public NotFoundException(String message, String type) {
        super(message);
        this.type = type;
    }
}
