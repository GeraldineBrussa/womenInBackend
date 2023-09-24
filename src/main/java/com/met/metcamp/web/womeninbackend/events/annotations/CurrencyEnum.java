package com.met.metcamp.web.womeninbackend.events.annotations;

import com.met.metcamp.web.womeninbackend.events.model.Currency;
import com.met.metcamp.web.womeninbackend.events.validations.CurrencyValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CurrencyValidation.class)
public @interface CurrencyEnum  {
    Currency [] anyOf();
    String message() default "Invalid currency, must be any of {anyOf}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
