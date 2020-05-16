package com.example.eureka_common.exceptions;

public class NotFoundException extends Exception {

    private static final long serialVersionUID = 3745574889653702671L;

    public NotFoundException(String message) {
        super(message);
    }
}