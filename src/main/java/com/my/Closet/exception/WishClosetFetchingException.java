package com.my.Closet.exception;


import java.util.UUID;

public class WishClosetFetchingException extends RuntimeException {
    public WishClosetFetchingException(String message) {
        super(message);
    }
    public WishClosetFetchingException(String message, String e) {
        super(message);
    }

    public WishClosetFetchingException(String message, Exception e) {

    }
}
