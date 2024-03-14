package com.my.Closet.exception;

public class JerseyServiceException extends RuntimeException {
    public JerseyServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
