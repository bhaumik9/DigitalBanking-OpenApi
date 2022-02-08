package com.casestudy.digitalbankingopenapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ExceptionResponse {

    private String errorCode;
    private String description;
}
