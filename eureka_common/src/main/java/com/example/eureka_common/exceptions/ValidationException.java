package com.example.eureka_common.exceptions;

public class ValidationException extends Exception {

    private static final long serialVersionUID = 3900752163452430603L;

    public ValidationException(String message) {
        super(message);
    }
}