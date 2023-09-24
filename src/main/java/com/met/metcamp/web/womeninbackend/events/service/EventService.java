package com.met.metcamp.web.womeninbackend.events.service;


import com.met.metcamp.web.womeninbackend.events.exceptions.ApiException;
import com.met.metcamp.web.womeninbackend.events.model.Event;
import com.met.metcamp.web.womeninbackend.events.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EventService {
    private final EventRepository repository;
    @Autowired
    public EventService( EventRepository repository) {
        this.repository = repository;
    }
    public ArrayList<Event> getAllEvents() {
        return repository.getEvents();
    }
    public Event getEventById(int id) {
        return repository.find(id)
                .orElseThrow(() -> new ApiException(404, String.format("Event with ID %s not found", id)));
    }
    public Event createEvent(Event event) {
        repository.find(event.getId()).ifPresentOrElse(
                existingEvent -> {
                    throw new ApiException(409, String.format("Event with ID %s already exists when creating an " +
                            "event", event.getId()));
                },
                () -> repository.add(event)
        );
        return event;
    }
    public Event updateEvent(int id, Event event) {
        return repository.find(id)
                .map(foundEvent -> {
                    repository.update(id, event);
                    return event;
                })
                .orElseThrow(() -> new ApiException(404, String.format("Event with ID %s not found", id)));
    }
    public void deleteEvent(int id) {
        repository.find(id)
                .ifPresentOrElse(
                        foundEvent -> repository.delete(foundEvent.getId()),
                        () -> {
                            throw new ApiException(404, String.format("Event with ID %s not found", id));
                        }
                );
    }
}