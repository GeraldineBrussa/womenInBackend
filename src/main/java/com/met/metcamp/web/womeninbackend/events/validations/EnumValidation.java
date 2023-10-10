package com.met.metcamp.web.womeninbackend.events.validations;

import com.met.metcamp.web.womeninbackend.events.annotations.EnumValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


public class EnumValidation implements ConstraintValidator<EnumValue, Enum<?>> {
    private Set<String> prohibitedValues;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        this.prohibitedValues = Arrays.stream(constraintAnnotation.prohibitedValues()).collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null || prohibitedValues.contains(value.name())) {
            return false;
        }
        return true;
    }
}