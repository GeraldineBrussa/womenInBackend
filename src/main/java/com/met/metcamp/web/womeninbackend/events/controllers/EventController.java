package com.met.metcamp.web.womeninbackend.events.controllers;


import com.met.metcamp.web.womeninbackend.events.exceptions.*;
import com.met.metcamp.web.womeninbackend.events.model.Event;
import com.met.metcamp.web.womeninbackend.events.service.EventService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.met.metcamp.web.womeninbackend.events.utils.AppConstants.eventControllerMessages.*;

@RestController
@RequestMapping("/met/metcamp/web/womeninbackend/events")
public class EventController {
    private final EventService eventService;
    private static final Logger logger = LogManager.getLogger(EventService.class);
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        try {
            List<Event> events = eventService.getAllEvents();
            return events.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(events);
        } catch (Exception e) {
            logger.error(ERROR_MESSAGE_GET_ALL_GENERIC_EXCEPTION
                    , e);
            return handleInternalServerError(e);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEventById(@PathVariable int id) {
        try {
            Event event = eventService.getEventById(id);
            return ResponseEntity.ok(event);
        } catch (NotFoundException e) {
            logger.error(ERROR_MESSAGE_GET_BY_ID_NOT_FOUND + id +": " + e.getMessage());
            return handleNotFoundException(e);
        } catch (Exception e) {
            logger.error(ERROR_MESSAGE_GET_BY_ID_GENERIC_EXCEPTION + id + ": ", e);
            return handleInternalServerError(e);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createEvent(@RequestBody String body) {
        try {
            Event event = eventService.createEvent(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(event);
        } catch (AlreadyExistsException e) {
            logger.error(ERROR_MESSAGE_CREATE_ALREADY_EXISTS +": " + e.getMessage());
            return handleAlreadyExistsException(e);
        } catch (ValidationException e) {
            logger.error(ERROR_MESSAGE_CREATE_VALIDATION_EXCEPTION + e.getMessage());
            return handleValidationException(e);
        } catch (ConversionException e) {
            logger.error(ERROR_MESSAGE_CREATE_CONVERSION_EXCEPTION + e.getMessage());
            return handleConversionException(e);
        } catch (RepoException e) {
            logger.error(ERROR_MESSAGE_CREATE_REPO_EXCEPTION + e.getMessage());
            return handleRepoException(e);
        } catch (Exception e) {
            logger.error(ERROR_MESSAGE_CREATE_GENERIC_EXCEPTION + e.getMessage());
            return handleInternalServerError(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEventById(@PathVariable int id, @RequestBody String body) {
        try {
            Event updatedEvent = eventService.updateEvent(id, body);
            return ResponseEntity.ok(updatedEvent);
        } catch (NotFoundException e) {
            logger.error(ERROR_MESSAGE_UPDATE_ID_NOT_FOUND + id + ": " + e.getMessage());
            return handleNotFoundException(e);
        } catch (ValidationException e) {
            logger.error(ERROR_MESSAGE_UPDATE_VALIDATION_EXCEPTION  + id + ": " + e.getMessage());
            return handleValidationException(e);
        } catch (ConversionException e) {
            logger.error(ERROR_MESSAGE_UPDATE_CONVERSION_EXCEPTION + id + ": " + e.getMessage());
            return handleConversionException(e);
        } catch (RepoException e) {
            logger.error(ERROR_MESSAGE_UPDATE_REPO_EXCEPTION + id + ": " + e.getMessage());
            return handleRepoException(e);
        }  catch (Exception e) {
            logger.error(ERROR_MESSAGE_UPDATE_GENERIC_EXCEPTION + id + ": " + e.getMessage());
            return handleInternalServerError(e);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEventById(@PathVariable int id) {
        try {
            boolean deleted = eventService.deleteEvent(id);
            return deleted ? ResponseEntity.noContent().header("X-Message", "Event Deleted").build() : ResponseEntity.notFound().build();
        } catch (NotFoundException e) {
            logger.error(ERROR_MESSAGE_DELETE_ID_NOT_FOUND + id + ": " + e.getMessage());
            return handleNotFoundException(e);
        } catch (RepoException e){
            logger.error(ERROR_MESSAGE_DELETE_REPO_EXCEPTION + id + ": " + e.getMessage());
            return handleRepoException(e);
        } catch (Exception e) {
            logger.error(ERROR_MESSAGE_DELETE_GENERIC_EXCEPTION + id + ": " + e.getMessage());
            return handleInternalServerError(e);
        }
    }
    private ResponseEntity<Object> handleValidationException(ValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private ResponseEntity<Object> handleConversionException(ConversionException e) {
        return ResponseEntity.badRequest().body(ERROR_MESSAGE_HANDLE_CONVERSION_EXCEPTION_BODY + e.getMessage());
    }

    private ResponseEntity<Object> handleRepoException(RepoException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
    private ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    private ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    private ResponseEntity<Object> handleInternalServerError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_MESSAGE_GENERIC_EXCEPTION_BODY);
    }

}

