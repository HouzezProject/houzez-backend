package com.eta.houzezbackend.exception;

import io.jsonwebtoken.JwtException;

public class LinkExpiredException extends JwtException {

    public LinkExpiredException(String message) {
        super(message + " Link Expired!");
    }
}
