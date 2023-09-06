package com.met.metcamp.web.womeninbackend.events.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.met.metcamp.web.womeninbackend.events.exceptions.EventAlreadyExistsException;
import com.met.metcamp.web.womeninbackend.events.exceptions.EventNotFoundException;
import com.met.metcamp.web.womeninbackend.events.model.Event;
import com.met.metcamp.web.womeninbackend.events.repository.EventRepository;
import com.met.metcamp.web.womeninbackend.events.utils.MapperUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

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
        return repository.getEvents();
    }

    public Optional<Event> getEventById(int id) {
        return repository.find(id);
    }

    public Event createEvent(String json) throws JsonProcessingException {
        Event event = mapperUtils.mapToEvent(json);
        validationService.validateCreateEvent(event);
        Optional<Event> foundEvent = repository.find(event.getId());

        if (foundEvent.isPresent()) {
            throw new EventAlreadyExistsException("Event already exists");
        } else {
            repository.add(event);
            return event;
        }
    }

    public Event updateEvent(int id, String json) throws JsonProcessingException {
        Optional<Event> foundEvent = repository.find(id);

        if (foundEvent.isPresent()) {
            Event newEventData = mapperUtils.mapToEvent(json);
            validationService.validateUpdateEvent(newEventData);
            repository.update(id, newEventData);
            return newEventData;
        } else {
            throw new EventNotFoundException("Event not found with ID: " + id);
        }
    }

    public boolean deleteEvent(int id) {
        Optional<Event> foundEvent = repository.find(id);

        if (foundEvent.isPresent()) {
            repository.delete(foundEvent.get().getId());
            return true;
        } else {
            return false;
        }
    }
}
