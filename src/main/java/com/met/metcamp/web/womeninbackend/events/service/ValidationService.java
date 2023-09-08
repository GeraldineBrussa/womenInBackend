package com.met.metcamp.web.womeninbackend.events.service;



import com.met.metcamp.web.womeninbackend.events.exceptions.ValidationException;
import com.met.metcamp.web.womeninbackend.events.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ValidationService {
    public void validateCreateEvent(Event event){
        validateId(event.getId());
        validateName(event.getName());
        validateDates(event.getStartDate(), event.getEndDate());
        validateAttendees(event.getAttendees());
        validateName(event.getOrganizer());
        validateEventType(String.valueOf(event.getType()));
        validatePrices(event.getPrices());
    }
    
    public void validateUpdateEvent (Event event){
        validateName(event.getName());
        validateDates(event.getStartDate(), event.getEndDate());
        validateAttendees(event.getAttendees());
        validateName(event.getOrganizer());
        validateEventType(String.valueOf(event.getType()));
        validatePrices(event.getPrices());
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
    public void validateEventType(String type){
        if (type.isEmpty()){
            throw new ValidationException("Event type is required");
        }

        if (!isValidEventType(type)) {
            throw new ValidationException("Invalid event type");
        }
    }

    public void validatePrices(List<Price> prices) {
        if (prices != null && !prices.isEmpty()) {
            for (Price price : prices) {
                validatePrice(price);
            }
        }
}
    private boolean isValidEventType(String type) {
        for (EventType eventType : EventType.values()) {
            if (eventType.name().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }
    private void validatePrice(Price price) {
        if (!isValidTicketType(String.valueOf(price.getType()))) {
            throw new ValidationException("Invalid ticket type");
        }
        if (!isValidCurrency(String.valueOf(price.getValue()))) {
            throw new ValidationException("Invalid currency");
        }
        if (price.getValue() <= 0) {
            throw new ValidationException("Price value must be greater than 0.");
        }
    }

    private boolean isValidCurrency(String currencyValue) {
        for (Currency currency : Currency.values()) {
            if (currency.name().equalsIgnoreCase(currencyValue)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidTicketType(String type) {
        for (TicketType ticketType : TicketType.values()) {
            if (ticketType.name().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }


    }
/*
*
* Mejoras sugeridas por chat gpt para la validación de enums:
* private <T extends Enum<T>> void validateEnumValue(String value, Class<T> enumClass, String fieldName) {
    if (StringUtils.isEmpty(value)) {
        throw new ValidationException(fieldName + " is required");
    }

    try {
        Enum.valueOf(enumClass, value);
    } catch (IllegalArgumentException e) {
        throw new ValidationException("Invalid " + fieldName);
    }
}

public void validateEventType(String type) {
    validateEnumValue(type, EventType.class, "Event type");
}
* public void validatePrice(Price price) {
    if (price == null) {
        throw new ValidationException("Price is required");
    } Esto lo sacaría porque el precio no lo teníamos como obligatorio

    validateEnumValue(String.valueOf(price.getType()), TicketType.class, "Ticket type");
    validateEnumValue(String.valueOf(price.getCurrency()), Currency.class, "Currency");

    if (price.getValue() <= 0) {
        throw new ValidationException("Price value must be greater than 0.");
    }
}
*
*
* */
