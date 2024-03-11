package com.my.Closet.exception;

public class ClosetNotFoundException extends RuntimeException {

    public ClosetNotFoundException(String message) {
        super(message);
    }
}