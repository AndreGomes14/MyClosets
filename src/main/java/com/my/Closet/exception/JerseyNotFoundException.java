package com.my.Closet.exception;

public class JerseyNotFoundException extends RuntimeException {
    public JerseyNotFoundException(String message) {
        super(message);
    }
}
