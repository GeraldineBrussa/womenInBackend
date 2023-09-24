package com.met.metcamp.web.womeninbackend.events.exceptions;

public class ConversionException extends RuntimeException{
public ConversionException(Throwable cause){
    super("Error Mapping Event", cause);
}
}
