package com.eta.houzezbackend.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("Resource is not Found");
    }

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " " + id + " not found");
    }
}
