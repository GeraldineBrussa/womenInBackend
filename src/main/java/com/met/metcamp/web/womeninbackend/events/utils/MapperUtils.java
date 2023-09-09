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

import static com.met.metcamp.web.womeninbackend.events.utils.AppConstants.mapperUtilsMessages.*;

@Component
public class MapperUtils {
    private static  final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger(MapperUtils.class);
    public Event mapToEvent(String input){
        try {
            Event event = MAPPER.readValue(input, Event.class);
            logger.info(SUCCESS_MESSAGE_MAPPED_JSON_TO_EVENT);
            return event;
        } catch (JsonProcessingException e) {
            logger.error(ERROR_MESSAGE_MAPPED_JSON_TO_EVENT + e.getMessage(), e);
            throw new ConversionException(e);
        }
    }
    public ArrayList<Event> mapToEventList (String input){
        try {
            TypeReference<ArrayList<Event>> typeRef = new TypeReference<>() {};
            ArrayList<Event> eventList = MAPPER.readValue(input, typeRef);
            logger.info(SUCCESS_MESSAGE_MAPPED_JSON_TO_EVENT_LIST);
            return eventList;
        } catch (JsonProcessingException e){
            logger.error(ERROR_MESSAGE_MAPPED_JSON_TO_EVENT_LIST + e.getMessage(), e);
            throw new ConversionException(e);
        }
    }
    public String mapToJson (Event event){
        try {
            String jsonEvent = MAPPER.writeValueAsString(event);
            logger.info(SUCCESS_MESSAGE_MAPPED_EVENT_TO_JSON);
            return jsonEvent;
        } catch (JsonProcessingException e){
            logger.error(ERROR_MESSAGE_MAPPED_EVENT_TO_JSON + e.getMessage(), e);
            throw new ConversionException(e);
        }
    }
    public String mapToJson(ArrayList<Event> eventList){
        try {
            String jsonEventList = MAPPER.writeValueAsString(eventList);
            logger.info(SUCCESS_MESSAGE_MAPPED_EVENT_LIST_TO_JSON);
            return jsonEventList;
        }catch (JsonProcessingException e){
            logger.error(ERROR_MESSAGE_MAPPED_EVENT_LIST_TO_JSON + e.getMessage(), e);
            throw new ConversionException(e);
        }
    }
}
