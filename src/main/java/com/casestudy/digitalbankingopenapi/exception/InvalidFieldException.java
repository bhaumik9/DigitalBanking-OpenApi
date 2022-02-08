package com.casestudy.digitalbankingopenapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidFieldException extends RuntimeException{
    String message;
    public InvalidFieldException(String message) {
        this.message=message;
    }
}
