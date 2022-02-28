package com.casestudy.digitalbankingopenapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidFieldException extends RuntimeException{
    final String message;
    final String type;
    public InvalidFieldException(String message, String type) {
        this.message=message;
        this.type = type;
    }
}
