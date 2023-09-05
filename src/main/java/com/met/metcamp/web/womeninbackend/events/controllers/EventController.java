package com.met.metcamp.web.womeninbackend.events.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.met.metcamp.web.womeninbackend.events.model.Event;
import com.met.metcamp.web.womeninbackend.events.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable int id) {
        Optional<Event> event = eventService.getEventById(id);
        if (event.isPresent()) {
            return ResponseEntity.ok(event.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> createEvent(@RequestBody String body) {
        try {
            Event event = eventService.createEvent(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(event);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("Malformed Event");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEventById(@PathVariable int id, @RequestBody String body) {
        try {
            Event updatedEvent = eventService.updateEvent(id, body);
            return ResponseEntity.ok(updatedEvent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("Malformed Event");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEventById(@PathVariable int id) {
        boolean deleted = eventService.deleteEvent(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}


/*
 *
 *
 *
 *  @GetMapping
    public ResponseEntity getAllEvents(){
        return ResponseEntity.ok(Map.of("events:", "[GET Lista de eventos]"));
    }
    @GetMapping("/{id}")
    public ResponseEntity getEventById(@PathVariable int id) {
        return ResponseEntity.ok(Map.of("events:", String.format("[GET evento con id %s]", id)));
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody String body) {
        if (body.contains("*")) {
            return ResponseEntity.badRequest().body("No se permiten caracteres especiales");
        } else {
            return ResponseEntity.ok(Map.of("datos recibidos:", body));
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity updateEventById(@PathVariable int id) {
        return ResponseEntity.ok(Map.of("events:", String.format("[PUT evento con id %s]", id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEventById(@PathVariable int id) {
        return ResponseEntity.ok(Map.of("events:", String.format("[Delete evento con id %s]", id)));
    }
 *
 *
 *
 */