package com.met.metcamp.web.womeninbackend.events.exceptions;

public class ConvertionException extends RuntimeException{
public ConvertionException(Throwable cause){
    super("Error Mapping Event", cause);
}
}
