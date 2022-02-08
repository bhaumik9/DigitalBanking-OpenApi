package com.casestudy.digitalbankingopenapi.exception.customer_update_exceptions;

public class InvalidPhoneNumber extends RuntimeException{
    public InvalidPhoneNumber() {
        super("Invalid Phone Number exception");
    }
}
