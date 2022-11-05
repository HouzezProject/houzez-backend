package com.eta.houzezbackend.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class UniquePropertyViolationException extends DataIntegrityViolationException {
    public UniquePropertyViolationException() {
        super("Property is Not unique");
    }
}
