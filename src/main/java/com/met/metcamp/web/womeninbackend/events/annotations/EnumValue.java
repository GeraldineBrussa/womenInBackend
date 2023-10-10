package com.met.metcamp.web.womeninbackend.events.annotations;

import com.met.metcamp.web.womeninbackend.events.validations.EnumValidation;
//import com.met.metcamp.web.womeninbackend.events.validations.EnumValidationFactory;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidation.class)
public @interface EnumValue {
    String[] prohibitedValues() default {};
    String message() default "Invalid enum value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
