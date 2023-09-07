package com.met.metcamp.web.womeninbackend.events.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.met.metcamp.web.womeninbackend.events.exceptions.ConversionException;
import com.met.metcamp.web.womeninbackend.events.model.Event;
import com.met.metcamp.web.womeninbackend.events.service.EventService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
@Component
public class MapperUtils {
    private static  final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger(EventService.class);
    public Event mapToEvent(String input){
        try {
            return MAPPER.readValue(input, Event.class);
        } catch (JsonProcessingException e) {
            throw new ConversionException(e);
        }
    }
    public ArrayList<Event> mapToEventList (String input){
        try {
            TypeReference<ArrayList<Event>> typeRef = new TypeReference<>(){};
            return MAPPER.readValue(input, typeRef);
        } catch (JsonProcessingException e){
            throw new ConversionException(e);
        }
    }
    public String mapToJson (Event event){
        try {
            return MAPPER.writeValueAsString(event);
        } catch (JsonProcessingException e){
            throw new ConversionException(e);
        }
    }
    public String mapToJson(ArrayList<Event> eventList){
        try {
            return MAPPER.writeValueAsString(eventList);
        }catch (JsonProcessingException e){
            throw new ConversionException(e);
        }
    }
}
