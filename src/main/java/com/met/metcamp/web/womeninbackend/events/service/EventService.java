package com.met.metcamp.web.womeninbackend.events.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.met.metcamp.web.womeninbackend.events.exceptions.AlreadyExistsException;
import com.met.metcamp.web.womeninbackend.events.exceptions.NotFoundException;
import com.met.metcamp.web.womeninbackend.events.model.Event;
import com.met.metcamp.web.womeninbackend.events.repository.EventRepository;
import com.met.metcamp.web.womeninbackend.events.utils.MapperUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import static com.met.metcamp.web.womeninbackend.events.utils.AppConstants.*;

@Service
public class EventService {
    private static final Logger logger = LogManager.getLogger(EventService.class);
    private final MapperUtils mapperUtils;
    private final EventRepository repository;
    private final ValidationService validationService;

    @Autowired
    public EventService(MapperUtils mapperUtils, EventRepository repository, ValidationService validationService) {
        this.mapperUtils = mapperUtils;
        this.repository = repository;
        this.validationService = validationService;
    }


    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> events = repository.getEvents();
        if (events.isEmpty()) {
            logger.info(SUCCESS_MESSAGE_FETCHED + ", but the list is empty");
        } else {
            logger.info(SUCCESS_MESSAGE_FETCHED);
        }
        return events;
    }

    public Event getEventById(int id) {
        Optional<Event> event = repository.find(id);
        if (event.isPresent()) {
            logger.info("Event found with ID: " + id);
            return event.get();
        } else {
            logger.error(ERROR_MESSAGE_NOT_FOUND + id);
            throw new NotFoundException(ERROR_MESSAGE_NOT_FOUND + id);
        }
    }

    public Event createEvent(String json) throws JsonProcessingException {
        Event event = mapperUtils.mapToEvent(json);
        validationService.validateCreateEvent(event);
        Optional<Event> foundEvent = repository.find(event.getId());

        if (foundEvent.isPresent()) {
            logger.error(ERROR_MESSAGE_ALREADY_EXISTS);
            throw new AlreadyExistsException(ERROR_MESSAGE_ALREADY_EXISTS);

        } else {
            repository.add(event);
            logger.info(SUCCESS_MESSAGE_CREATED);
            return event;
        }
    }

    public Event updateEvent(int id, String json) throws JsonProcessingException {
        Optional<Event> foundEvent = repository.find(id);

        if (foundEvent.isPresent()) {
            Event newEventData = mapperUtils.mapToEvent(json);
            validationService.validateUpdateEvent(newEventData);
            repository.update(id, newEventData);
            logger.info(SUCCESS_MESSAGE_UPDATED);
            return newEventData;
        } else {
            logger.error(ERROR_MESSAGE_NOT_FOUND + id );
            throw new NotFoundException(ERROR_MESSAGE_NOT_FOUND + id);
        }
    }

    public boolean deleteEvent(int id) {
        Optional<Event> foundEvent = repository.find(id);

        if (foundEvent.isPresent()) {
            repository.delete(foundEvent.get().getId());
            logger.info(SUCCESS_MESSAGE_DELETED);
            return true;
        } else {
            logger.error(ERROR_MESSAGE_NOT_FOUND + id);
            throw new NotFoundException(ERROR_MESSAGE_NOT_FOUND + id);
        }
    }
}
