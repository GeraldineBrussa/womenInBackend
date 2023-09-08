package com.met.metcamp.web.womeninbackend.events.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.met.metcamp.web.womeninbackend.events.exceptions.ConversionException;
import com.met.metcamp.web.womeninbackend.events.model.Event;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
@Component
public class MapperUtils {
    private static  final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger(MapperUtils.class);
    public Event mapToEvent(String input){
        try {
            Event event = MAPPER.readValue(input, Event.class);
            logger.info("Mapped JSON to Event successfully");
            return event;
        } catch (JsonProcessingException e) {
            logger.error("Error mapping JSON to Event: " + e.getMessage(), e);
            throw new ConversionException(e);
        }
    }
    public ArrayList<Event> mapToEventList (String input){
        try {
            TypeReference<ArrayList<Event>> typeRef = new TypeReference<>() {};
            ArrayList<Event> eventList = MAPPER.readValue(input, typeRef);
            logger.info("Mapped JSON to Event List successfully");
            return eventList;
        } catch (JsonProcessingException e){
            logger.error("Error mapping JSON to Event List: " + e.getMessage(), e);
            throw new ConversionException(e);
        }
    }
    public String mapToJson (Event event){
        try {
            String jsonEvent = MAPPER.writeValueAsString(event);
            logger.info("Mapped Event to JSON successfully");
            return jsonEvent;
        } catch (JsonProcessingException e){
            logger.error("Error mapping Event to JSON: " + e.getMessage(), e);
            throw new ConversionException(e);
        }
    }
    public String mapToJson(ArrayList<Event> eventList){
        try {
            String jsonEventList = MAPPER.writeValueAsString(eventList);
            logger.info("Mapped Event List to JSON successfully");
            return jsonEventList;
        }catch (JsonProcessingException e){
            logger.error("Error mapping Event List to JSON: " + e.getMessage(), e);
            throw new ConversionException(e);
        }
    }
}
