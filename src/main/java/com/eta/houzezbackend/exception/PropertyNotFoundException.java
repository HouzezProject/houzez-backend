package com.eta.houzezbackend.exception;

public class PropertyNotFoundException extends RuntimeException {
    public PropertyNotFoundException() {
        super("Something wrong with the request criteria.");
    }
}
