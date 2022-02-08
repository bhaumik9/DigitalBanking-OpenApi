package com.casestudy.digitalbankingopenapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MandatoryFieldMissingException extends RuntimeException{
    final String type;
    public MandatoryFieldMissingException(String type){
        super("Mandatory Field Missing for "+type);
        this.type=type;
    }
}
