package com.met.metcamp.web.womeninbackend.events.validations;

import com.met.metcamp.web.womeninbackend.events.annotations.EndDateAfterStartDate;
import com.met.metcamp.web.womeninbackend.events.model.Event;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EndDateAfterStartDateValidation  implements ConstraintValidator<EndDateAfterStartDate, Event> {
    @Override
    public void initialize(EndDateAfterStartDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(Event event, ConstraintValidatorContext context) {
        if (event.getEndDate() == null || event.getStartDate() == null){
            return true;
        }
        return event.getEndDate().isAfter(event.getStartDate());
    }
}
