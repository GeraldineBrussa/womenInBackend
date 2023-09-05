package com.met.metcamp.web.womeninbackend.events.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String message){
        super(message);
    }
}
