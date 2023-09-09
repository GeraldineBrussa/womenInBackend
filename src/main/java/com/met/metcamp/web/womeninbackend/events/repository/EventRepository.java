package com.met.metcamp.web.womeninbackend.events.repository;

import com.met.metcamp.web.womeninbackend.events.exceptions.RepoException;
import com.met.metcamp.web.womeninbackend.events.model.Event;
import com.met.metcamp.web.womeninbackend.events.utils.MapperUtils;
import lombok.Getter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

import static com.met.metcamp.web.womeninbackend.events.utils.AppConstants.eventRepositoryMessages.*;

@Repository
public class EventRepository {
    private static final Logger logger = LogManager.getLogger(EventRepository.class);
    @Getter
    private final ArrayList<Event> events;
    private final MapperUtils mapperUtils;
    public EventRepository(MapperUtils mapperUtils){
        this.mapperUtils = mapperUtils;
        this.events = loadEvents();
        logger.info(INITIALIZED_EVENT_REPOSITORY);
    }

    private ArrayList<Event> loadEvents() {
        try{
            byte[] bytes = Files.readAllBytes(Paths.get("src/main/resources/repository/events.json"));
            String input = new String(bytes);
            logger.info(SUCCESS_MESSAGE_LOADED_EVENTS);
            return this.mapperUtils.mapToEventList(input);
        }catch (IOException io){
            logger.error(ERROR_MESSAGE_READING_FILE + io.getMessage(), io);
            throw new RepoException(ERROR_MESSAGE_READING_FILE);
        }
    }
    private void save(){
        try {
            String data = mapperUtils.mapToJson(events);
            Files.writeString(Paths.get("src/main/resources/repository/events.json"), data);
            logger.info(SUCCESS_MESSAGE_SAVED_EVENTS);
        } catch (IOException io){
            logger.error(": " + io.getMessage(), io);
            throw new RepoException(ERROR_MESSAGE_WRITING_FILE);
        }
    }
    public Optional<Event> find (int id){
        Optional<Event> result = Optional.empty();
        for (Event e: events){
            if (e.getId() == id){
                result = Optional.of(e);
            }
        }
        logger.info(SUCCESS_MESSAGE_FOUND_EVENT_BY_ID + id);
        return result;
    }
    public void add (Event newEvent){
        events.add(newEvent);
        logger.info(SUCCESS_MESSAGE_ADDED + newEvent.getId());
        save();
    }
    public void delete (int id){
        Optional<Event> foundEvent = find(id);
        if (foundEvent.isPresent()){
            events.remove(foundEvent.get());
            logger.info(SUCCESS_MESSAGE_DELETED + id);
            save();
        }
    }
    public void update(int id, Event updateInfo){
        Optional<Event> optionalEvent = find(id);
        if (optionalEvent.isPresent()){
            Event foundEvent = optionalEvent.get();
            events.remove(foundEvent);
            foundEvent.update(updateInfo);
            events.add(foundEvent);
            logger.info(SUCCESS_MESSAGE_UPDATED + id);
            save();
        }
    }
}
