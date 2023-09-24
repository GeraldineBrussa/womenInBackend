package com.met.metcamp.web.womeninbackend.events.validations;

import com.met.metcamp.web.womeninbackend.events.annotations.CurrencyEnum;
import com.met.metcamp.web.womeninbackend.events.model.Currency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class CurrencyValidation implements ConstraintValidator<CurrencyEnum, Currency> {
    Currency [] subset;
    @Override
    public void initialize(CurrencyEnum constraintAnnotation) {
        this.subset = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(Currency value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
