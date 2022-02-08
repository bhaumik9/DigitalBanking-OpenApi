package com.casestudy.digitalbankingopenapi.exception.customer_update_exceptions;

public class MandatoryFieldMissing extends RuntimeException{
    public MandatoryFieldMissing(){
        super("Mandatory Field Missing");
    }
}
