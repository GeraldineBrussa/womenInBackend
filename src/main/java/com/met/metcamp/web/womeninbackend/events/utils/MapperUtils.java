package com.met.metcamp.web.womeninbackend.events.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.met.metcamp.web.womeninbackend.events.exceptions.ConversionException;
import com.met.metcamp.web.womeninbackend.events.model.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


import java.util.ArrayList;

@Component
public class MapperUtils {
    public static  final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger(MapperUtils.class);
    public static Event mapToEvent(String input){
        try {
            Event event = MAPPER.readValue(input, Event.class);
            logger.info("Mapped JSON to Event successfully");
            return event;
        } catch (JsonProcessingException e) {
            logger.error("Error mapping JSON to Event: %s", e);
            throw new ConversionException(e);
        }
    }
    public static ArrayList<Event> mapToEventList (String input){
        try {
            TypeReference<ArrayList<Event>> typeRef = new TypeReference<>() {};
            logger.info("Mapped JSON to Event List successfully");
            return MAPPER.readValue(input, typeRef);
        } catch (JsonProcessingException e){
            logger.error("Error mapping JSON to Event List: %s", e);
            throw new ConversionException(e);
        }
    }
    public static String mapToJson (Event event){
        try {
            logger.info("Mapped Event to JSON successfully");
            return MAPPER.writeValueAsString(event);
        } catch (JsonProcessingException e){
            logger.error("Error mapping Event to JSON: %s", e);
            throw new ConversionException(e);
        }
    }
    public static String mapToJson(ArrayList<Event> eventList){
        try {
            logger.info("Mapped Event List to JSON successfully");
            return MAPPER.writeValueAsString(eventList);
        }catch (JsonProcessingException e){
            logger.error("Error mapping Event List to JSON: %s", e);
            throw new ConversionException(e);
        }
    }
}
