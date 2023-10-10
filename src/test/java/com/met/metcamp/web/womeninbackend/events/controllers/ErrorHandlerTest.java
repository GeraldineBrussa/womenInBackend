package com.met.metcamp.web.womeninbackend.events.controllers;

import com.met.metcamp.web.womeninbackend.events.exceptions.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(ErrorHandler.class)
public class ErrorHandlerTest {
    @InjectMocks
    private ErrorHandler errorHandler;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("InternalException - Returns Internal Server Error, code 500 and message")
    void testInternalExceptionHandler() {
        RuntimeException exception = new RuntimeException("Internal error message");
        ResponseEntity<Map<String, Object>> response = errorHandler.internalExceptionHandler(exception);
                assertEquals(response.getStatusCode().value(), 500);
                assertEquals(response.getBody(), Map.of("message", "Internal error"));
    }
    @Test
    @DisplayName("ApiException - Returns bad request error, code 404 and message")
    void testApiExceptionHandler () {
        ApiException exception = new ApiException(404, "Event with ID 4 not found");
        ResponseEntity<Map<String, Object>> response = errorHandler.apiExceptionHandler(exception);
        assertEquals(response.getStatusCode().value(), 404);
        assertEquals(response.getBody(), Map.of("message", "Event with ID 4 not found"));
    }
    @Test
    @DisplayName("ValidationException - Returns bad request, code 400 and Json of errors")
    void testValidationExceptionsHandle(){
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        List<ObjectError> errors = new ArrayList<>();
        errors.add(new FieldError("event", "name", "Name is required"));

        when(bindingResult.getAllErrors()).thenReturn(errors);
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Map<String, Object>> response = errorHandler.validationExceptionsHandle(methodArgumentNotValidException);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("errors"));

        Map<String, Object> actual = new HashMap<>();
        actual.put("errors", List.of("Name is required"));

        assertEquals(response.getBody(), actual);
    }
    @Test
    @DisplayName("GenericException - Returns internal server error, code 500 and message")
    void testGenericException (){
        Exception exception = new Exception();
        ResponseEntity<Map<String, Object>> response = errorHandler.genericException(exception);
        assertEquals(response.getStatusCode().value(), 500);
        assertEquals(response.getBody(),Map.of("message", "Generic error"));
    }
}
