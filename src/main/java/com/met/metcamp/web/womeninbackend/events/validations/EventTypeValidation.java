package com.met.metcamp.web.womeninbackend.events.validations;

import com.met.metcamp.web.womeninbackend.events.annotations.EventTypeEnum;
import com.met.metcamp.web.womeninbackend.events.model.EventType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EventTypeValidation implements ConstraintValidator<EventTypeEnum, EventType> {
    private EventType[] subset;

    @Override
    public void initialize(EventTypeEnum constraintAnnotation) {
        this.subset = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(EventType value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}