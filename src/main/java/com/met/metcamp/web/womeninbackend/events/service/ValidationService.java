package com.met.metcamp.web.womeninbackend.events.service;



import com.met.metcamp.web.womeninbackend.events.exceptions.ValidationException;
import com.met.metcamp.web.womeninbackend.events.model.Event;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class ValidationService {
    public void validateCreateEvent(Event event){
        validateId(event.getId());
        validateName(event.getName());
        validateDates(event.getStartDate(), event.getEndDate());
        validateAttendees(event.getAttendees());
    }
    
    public void validateUpdateEvent (Event event){
        validateName(event.getName());
        validateDates(event.getStartDate(), event.getEndDate());
        validateAttendees(event.getAttendees());
    }

    public void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime now = LocalDateTime.now();

        if (startDate == null || endDate == null) {
            throw new ValidationException("Both start date and end date are required.");
        }

        if (startDate.isBefore(now)) {
            throw new ValidationException("Start date must be in the future.");
        }

        if (endDate.isBefore(now)) {
            throw new ValidationException("End date must be in the future.");
        }

        if (startDate.isAfter(endDate)) {
            throw new ValidationException("Start date must be before end date.");
        }
    }

    public void validateName(String name) {
        if (name.isEmpty()){
            throw  new ValidationException("Name is required");
        }
        if (name.length() < 5){
            throw new ValidationException("Name is too short");
        }
    }
    public void validateId(int id) {
        if( id == 0){
            throw new ValidationException("ID must not be zero");
        } else if (id < 0) {
            throw new ValidationException("ID must be positive");
        }
    }
    public void validateAttendees (int  quantity){
        if (quantity <= 0) {
            throw new ValidationException("The number of event attendees must be greater than 0");
        }
        if (quantity > 50) {
            throw new ValidationException("The number of attendees to the event must be less than or equal to 50");
        }
    }
    
}
