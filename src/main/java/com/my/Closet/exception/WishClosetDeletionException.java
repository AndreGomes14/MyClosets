package com.my.Closet.exception;

public class WishClosetDeletionException extends RuntimeException {

    public WishClosetDeletionException(String message) {
        super(message);
    }

    public WishClosetDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
