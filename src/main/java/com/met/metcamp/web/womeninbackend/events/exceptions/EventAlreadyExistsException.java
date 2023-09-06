package com.met.metcamp.web.womeninbackend.events.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EventAlreadyExistsException extends RuntimeException{
    public EventAlreadyExistsException(String message) {
        super(message);
    }
}
