package com.casestudy.digitalbankingopenapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidFieldException extends RuntimeException{
    final String message;
    public InvalidFieldException(String message) {
        this.message=message;
    }
}
