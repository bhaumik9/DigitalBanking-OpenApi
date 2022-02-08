package com.casestudy.digitalbankingopenapi.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerNotFound extends RuntimeException {
    final String type;
    public CustomerNotFound(String type) {
        super("Customer not found");
        this.type=type;
    }
}
