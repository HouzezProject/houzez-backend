package com.eta.houzezbackend.exception;

public class AgentInactiveException extends RuntimeException {
    public AgentInactiveException() {
        super("This agent is not active!");
    }
}
