package com.my.Closet.exception;

import java.util.UUID;

public class WishJerseyNotFoundException extends RuntimeException {
    public WishJerseyNotFoundException(String message) {
        super(message);
    }

    public WishJerseyNotFoundException(String message, UUID id) {
        super(message);
    }
}
