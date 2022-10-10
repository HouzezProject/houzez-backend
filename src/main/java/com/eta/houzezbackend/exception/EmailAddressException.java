package com.eta.houzezbackend.exception;

public class EmailAddressException extends RuntimeException {
    public EmailAddressException() {
        super("The Email address is not valid.");
    }
}
