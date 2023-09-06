package com.met.metcamp.web.womeninbackend.events.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConversionException extends RuntimeException{
public ConversionException(Throwable cause){
    super("Error Mapping Event", cause);
}
}
