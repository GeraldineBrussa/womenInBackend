package com.met.metcamp.web.womeninbackend.events.controllers;

import com.met.metcamp.web.womeninbackend.events.model.Event;
import com.met.metcamp.web.womeninbackend.events.service.EventService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/met/metcamp/web/womeninbackend/events")
public class EventController {
    @Autowired
    private EventService eventService;
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEvents() {
        return eventService.getAllEvents().isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(Map.of("events", eventService.getAllEvents()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Event>> getEventById(@PathVariable int id) {
        return ResponseEntity.ok(Map.of("event", eventService.getEventById(id)));
    }
    @PostMapping
    public ResponseEntity<Map<String, Event>> createEvent(@Valid @RequestBody Event event) {
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("event", eventService.createEvent(event)));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Event>> updateEventById(@PathVariable int id, @Valid @RequestBody Event event) {
        return ResponseEntity.ok(Map.of("event", eventService.updateEvent(id, event)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEventById(@PathVariable int id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().header("X-Message", String.format("Event with ID %s successfully deleted", id)).build();
    }
}