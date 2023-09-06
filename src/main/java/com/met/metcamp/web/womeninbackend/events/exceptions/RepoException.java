package com.met.metcamp.web.womeninbackend.events.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RepoException extends RuntimeException{
    public RepoException(String message){
        super(message);
    }
}
