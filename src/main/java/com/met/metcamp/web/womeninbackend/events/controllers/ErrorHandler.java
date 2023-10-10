package com.met.metcamp.web.womeninbackend.events.controllers;

import com.met.metcamp.web.womeninbackend.events.exceptions.ApiException;
import com.met.metcamp.web.womeninbackend.events.exceptions.ConversionException;
import com.met.metcamp.web.womeninbackend.events.exceptions.RepoException;
import com.met.metcamp.web.womeninbackend.events.utils.MapperUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler {
    private static final Logger logger = LogManager.getLogger(MapperUtils.class);
    @ExceptionHandler({ConversionException.class, RepoException.class})
    protected ResponseEntity<Map<String, Object>> internalExceptionHandler(RuntimeException e) {
        logger.error("Internal Exception: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().body(Map.of("message", "Internal error"));
    }
    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<Map<String, Object>> apiExceptionHandler(ApiException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(e.getStatus()).body(Map.of("message", e.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validationExceptionsHandle(MethodArgumentNotValidException e) {
        logger.error("Validation errors: {}", e.getMessage(), e);
        List<String> errors = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .toList();
        return ResponseEntity.badRequest().body(Map.of("errors", errors));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Map<String, Object>> genericException(Exception e) {
        logger.error("Generic errorException: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().body(Map.of("message", "Generic error"));
    }
}
