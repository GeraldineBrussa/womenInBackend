package com.met.metcamp.web.womeninbackend.events.annotations;

import com.met.metcamp.web.womeninbackend.events.model.TicketType;
import com.met.metcamp.web.womeninbackend.events.validations.TicketTypeValidation;
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
@Constraint(validatedBy = TicketTypeValidation.class)
public @interface TicketTypeEnum {
    TicketType [] anyOf();
    String message() default "Invalid ticket type, must be any of {anyOf}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
