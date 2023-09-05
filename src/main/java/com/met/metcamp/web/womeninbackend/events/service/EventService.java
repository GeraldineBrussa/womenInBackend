package com.met.metcamp.web.womeninbackend.events.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
            throw new IllegalArgumentException("Event already exists");
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
            throw new IllegalArgumentException("Event Not Found");
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


/**
 *
 *
 *
 * private final MapperUtils mapperUtils;
 *     private final EventRepository repository;
 *     private final ValidationService validationService;
 *     public EventService(MapperUtils mapperUtils, EventRepository repository, ValidationService validationService){
 *         this.mapperUtils = mapperUtils;
 *         this.repository = repository;
 *         this.validationService = validationService;
 *     }
 *     public Event createEvent(String json){
 *         try {
 *             Event event = mapperUtils.mapToEvent(json);
 *             validationService.validateCreateEvent(event);
 *             Optional<Event> foundEvent = repository.find(event.getId());
 *             if (foundEvent.isPresent()){
 *                 return new Response(400,"Event already exists");
 *             } else {
 *                 repository.add(event);
 *                 return new EventResponse(201, "Event Created", event);
 *             }
 *         } catch (ConvertionException e){
 *             return new Response(400, "Malformed Event");
 *         } catch (RepoException e){
 *             return new Response(500, e.getMessage());
 *         }
 *     }
 *     public Event getAllEvents(){
 *         ArrayList<Event> temporalList = repository.getEvents();
 *         if (temporalList.isEmpty()){
 *             return new Response(204, "Event List Empty");
 *         } else {
 *             return new EventListResponse(200, "OK", temporalList);
 *         }
 *     }
 *     public Event getEventById (int id) {
 *         Optional<Event> foundEvent = repository.find(id);
 *         return foundEvent.isPresent() ? new EventResponse(200, "OK", foundEvent.get()) : new Response(404, "Event Not" +
 *                 " Found");
 *     }
 *     public Event updateEvent (int id, String json) throws JsonProcessingException{
 *         try {
 *             Optional<Event> foundEvent = repository.find(id);
 *             if (foundEvent.isPresent()){
 *                 Event newEventData = mapperUtils.mapToEvent(json);
 *                 validationService.validateUpdateEvent(newEventData);
 *                 repository.update(id, newEventData);
 *                 return new EventResponse(200, "Event Updated", newEventData);
 *             }else {
 *                 logger.info("The ID entered is {} and the data is {} ", id, json);
 *                 return new Response(404, "Event Not Found");
 *             }
 *         } catch (ConvertionException e){
 *             return new Response(404, "Malformed Event");
 *         } catch (RepoException e){
 *             return new Response(500, e.getMessage());
 *         }
 *     }
 *     public Event deleteEvent(int id){
 *         try {
 *             Optional<Event> foundEvent = repository.find(id);
 *             if (foundEvent.isPresent()){
 *                     repository.delete(foundEvent.get().getId());
 *                     return new Response(204, "Event Deleted");
 *                 } else {
 *                     return new Response(404, "Event Not Found");
 *                 }
 *         } catch (RepoException e){
 *             return new Response(500, e.getMessage());
 *         }
 *     }
 *
 *
 *
 **/