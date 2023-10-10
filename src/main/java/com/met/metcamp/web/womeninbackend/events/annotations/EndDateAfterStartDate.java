package com.met.metcamp.web.womeninbackend.events.annotations;

import com.met.metcamp.web.womeninbackend.events.validations.EndDateAfterStartDateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EndDateAfterStartDateValidation.class)
public @interface EndDateAfterStartDate {
    String message() default "endDate: Start date must be before end date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
