package com.casestudy.digitalbankingopenapi.exception;

public class InvalidPhoneNumberException extends RuntimeException{
    public InvalidPhoneNumberException(){
        super("Invalid Phone Number");
    }
}
