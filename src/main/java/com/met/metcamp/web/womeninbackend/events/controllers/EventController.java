package com.met.metcamp.web.womeninbackend.events.controllers;


import com.met.metcamp.web.womeninbackend.events.exceptions.*;
import com.met.metcamp.web.womeninbackend.events.model.Event;
import com.met.metcamp.web.womeninbackend.events.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("/met/metcamp/web/womeninbackend/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return events.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEventById(@PathVariable int id) {
        try {
            Optional<Event> event = eventService.getEventById(id);

            if (event.isPresent()) {
                return ResponseEntity.ok(event.get());
            } else {
                throw new EventNotFoundException("Event not found with ID: " + id);
            }
        } catch (EventNotFoundException e) {
            return handleEventNotFoundException(e);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createEvent(@RequestBody String body) {
        try {
            Event event = eventService.createEvent(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(event);
        } catch (ValidationException e) {
            return handleValidationException(e);
        } catch (ConversionException e) {
            return handleConversionException(e);
        } catch (RepoException e) {
            return handleRepoException(e);
        } catch (EventAlreadyExistsException e) {
            return handleEventAlreadyExistsException(e);
        } catch (Exception e) {
            return handleInternalServerError(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEventById(@PathVariable int id, @RequestBody String body) {
        try {
            Event updatedEvent = eventService.updateEvent(id, body);
            return ResponseEntity.ok(updatedEvent);
        } catch (ValidationException e) {
            return handleValidationException(e);
        } catch (ConversionException e) {
            return handleConversionException(e);
        } catch (RepoException e) {
            return handleRepoException(e);
        } catch (EventNotFoundException e) {
            return handleEventNotFoundException(e);
        } catch (Exception e) {
            return handleInternalServerError(e);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEventById(@PathVariable int id) {
        try {
            boolean deleted = eventService.deleteEvent(id);
            return deleted ? ResponseEntity.noContent().header("X-Message", "Event Deleted").build() : ResponseEntity.notFound().build();
        } catch (EventNotFoundException e) {
            return handleEventNotFoundException(e);
        } catch (RepoException e){
            return handleRepoException(e);
        }
    }
    private ResponseEntity<Object> handleValidationException(ValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private ResponseEntity<Object> handleConversionException(ConversionException e) {
        return ResponseEntity.badRequest().body("Malformed Event JSON: " + e.getMessage());
    }

    private ResponseEntity<Object> handleRepoException(RepoException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
    private ResponseEntity<Object> handleEventNotFoundException(EventNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    private ResponseEntity<Object> handleEventAlreadyExistsException(EventAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    private ResponseEntity<Object> handleInternalServerError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }

}

