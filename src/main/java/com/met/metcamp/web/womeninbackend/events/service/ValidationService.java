package com.met.metcamp.web.womeninbackend.events.service;


import com.met.metcamp.web.womeninbackend.events.exceptions.ValidationException;
import com.met.metcamp.web.womeninbackend.events.model.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.met.metcamp.web.womeninbackend.events.utils.AppConstants.validationServiceMessages.*;

@Service
public class ValidationService {

    private static final Logger logger = LogManager.getLogger(ValidationService.class);
    public void validateCreateEvent(Event event){
        validateId(event.getId());
        validateName(event.getName());
        validateDates(event.getStartDate(), event.getEndDate());
        validateAttendees(event.getAttendees());
        validateOrganizer(event.getOrganizer());
        validateEventType(String.valueOf(event.getType()));
        validatePrices(event.getPrices());
    }



    public void validateUpdateEvent (Event event){
        validateName(event.getName());
        validateDates(event.getStartDate(), event.getEndDate());
        validateAttendees(event.getAttendees());
        validateOrganizer(event.getOrganizer());
        validateEventType(String.valueOf(event.getType()));
        validatePrices(event.getPrices());
    }

    public void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime now = LocalDateTime.now();

        if (startDate == null || endDate == null) {
            logger.error(ERROR_MESSAGE_VALIDATION_DATES_START_END_REQUIRED);
            throw new ValidationException(ERROR_MESSAGE_VALIDATION_DATES_START_END_REQUIRED);
        }

        if (startDate.isBefore(now)) {
            logger.error(ERROR_MESSAGE_VALIDATION_DATES_START_IN_FUTURE);
            throw new ValidationException(ERROR_MESSAGE_VALIDATION_DATES_START_IN_FUTURE);
        }

        if (endDate.isBefore(now)) {
            logger.error(ERROR_MESSAGE_VALIDATION_DATES_END_IN_FUTURE);
            throw new ValidationException(ERROR_MESSAGE_VALIDATION_DATES_END_IN_FUTURE);
        }

        if (startDate.isAfter(endDate)) {
            logger.error(ERROR_MESSAGE_VALIDATION_DATES_START_BEFORE_END);
            throw new ValidationException(ERROR_MESSAGE_VALIDATION_DATES_START_BEFORE_END);
        }
    }

    public void validateName(String name) {
        if (name.isEmpty()){
            logger.error(ERROR_MESSAGE_VALIDATION_NAME_REQUIRED);
            throw  new ValidationException(ERROR_MESSAGE_VALIDATION_NAME_REQUIRED);
        }
        if (name.length() < 5){
            logger.error(ERROR_MESSAGE_VALIDATION_NAME_LENGTH);
            throw new ValidationException(ERROR_MESSAGE_VALIDATION_NAME_LENGTH);
        }
    }
    public void validateId(int id) {
        if( id == 0){
            logger.error(ERROR_MESSAGE_VALIDATION_ID_REQUIRED);
            throw new ValidationException(ERROR_MESSAGE_VALIDATION_ID_REQUIRED);
        } else if (id < 0) {
            logger.error(ERROR_MESSAGE_VALIDATION_ID_POSITIVE);
            throw new ValidationException(ERROR_MESSAGE_VALIDATION_ID_POSITIVE);
        }
    }
    public void validateOrganizer(String organizer) {
        if (organizer.isEmpty()){
            logger.error(ERROR_MESSAGE_VALIDATION_ORGANIZER_REQUIRED);
            throw  new ValidationException(ERROR_MESSAGE_VALIDATION_ORGANIZER_REQUIRED);
        }
        if (organizer.length() < 3){
            logger.error(ERROR_MESSAGE_VALIDATION_ORGANIZER_LENGTH);
            throw new ValidationException(ERROR_MESSAGE_VALIDATION_ORGANIZER_LENGTH);
        }
    }
    public void validateAttendees (int  quantity){
        if (quantity <= 0) {
            logger.error(ERROR_MESSAGE_VALIDATION_ATTENDEES_MINIMUM);
            throw new ValidationException(ERROR_MESSAGE_VALIDATION_ATTENDEES_MINIMUM);
        }
        if (quantity > 50) {
            logger.error(ERROR_MESSAGE_VALIDATION_ATTENDEES_MAXIMUM);
            throw new ValidationException(ERROR_MESSAGE_VALIDATION_ATTENDEES_MAXIMUM);
        }
    }
    public void validateEventType(String type){
        if (type.isEmpty()){
            logger.error(ERROR_MESSAGE_VALIDATION_EVENT_TYPE_REQUIRED);
            throw new ValidationException(ERROR_MESSAGE_VALIDATION_EVENT_TYPE_REQUIRED);
        }

        if (!isValidEventType(type)) {
            logger.error(ERROR_MESSAGE_VALIDATION_EVENT_TYPE_ENUM);
            throw new ValidationException(ERROR_MESSAGE_VALIDATION_EVENT_TYPE_ENUM);
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
            logger.error(ERROR_MESSAGE_VALIDATION_TICKET_TYPE_ENUM);
            throw new ValidationException(ERROR_MESSAGE_VALIDATION_TICKET_TYPE_ENUM);
        }
        if (!isValidCurrency(String.valueOf(price.getCurrency()))) {
            logger.error(ERROR_MESSAGE_VALIDATION_CURRENCY_ENUM);
            throw new ValidationException(ERROR_MESSAGE_VALIDATION_CURRENCY_ENUM);
        }
        if (price.getValue() <= 0) {
            logger.error(ERROR_MESSAGE_VALIDATION_VALUE_PRICE_MINIMUM);
            throw new ValidationException(ERROR_MESSAGE_VALIDATION_VALUE_PRICE_MINIMUM);
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
