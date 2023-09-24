package com.met.metcamp.web.womeninbackend.events.validations;

import com.met.metcamp.web.womeninbackend.events.annotations.TicketTypeEnum;
import com.met.metcamp.web.womeninbackend.events.model.TicketType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class TicketTypeValidation implements ConstraintValidator<TicketTypeEnum, TicketType> {
    private TicketType[] subset;
    @Override
    public void initialize(TicketTypeEnum constraintAnnotation) {
        this.subset = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(TicketType value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
