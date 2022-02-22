package com.casestudy.digitalbankingopenapi.exception;

public class FieldMissing extends RuntimeException {
    public FieldMissing(String field) {
        super("Customer " + field + " Field Missing");
    }
    public FieldMissing() {
        super("Customer Field Missing");
    }
}
