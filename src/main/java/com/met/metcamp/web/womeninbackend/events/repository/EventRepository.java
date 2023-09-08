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

@Repository
public class EventRepository {
    private static final Logger logger = LogManager.getLogger(EventRepository.class);
    @Getter
    private final ArrayList<Event> events;
    private final MapperUtils mapperUtils;
    public EventRepository(MapperUtils mapperUtils){
        this.mapperUtils = mapperUtils;
        this.events = loadEvents();
        logger.info("EventRepository initialized");
    }

    private ArrayList<Event> loadEvents() {
        try{
            byte[] bytes = Files.readAllBytes(Paths.get("src/main/resources/repository/events.json"));
            String input = new String(bytes);
            logger.info("Loaded events from file successfully");
            return this.mapperUtils.mapToEventList(input);
        }catch (IOException io){
            logger.error("Error reading file: " + io.getMessage(), io);
            throw new RepoException("Error reading file");
        }
    }
    private void save(){
        try {
            String data = mapperUtils.mapToJson(events);
            Files.writeString(Paths.get("src/main/resources/repository/events.json"), data);
            logger.info("Saved events to file successfully");
        } catch (IOException io){
            logger.error(": " + io.getMessage(), io);
            throw new RepoException("Error writing file");
        }
    }
    public Optional<Event> find (int id){
        Optional<Event> result = Optional.empty();
        for (Event e: events){
            if (e.getId() == id){
                result = Optional.of(e);
            }
        }
        logger.info("Event found with ID: " + id);
        return result;
    }
    public void add (Event newEvent){
        events.add(newEvent);
        logger.info("Event added successfully with ID: " + newEvent.getId());
        save();
    }
    public void delete (int id){
        Optional<Event> foundEvent = find(id);
        if (foundEvent.isPresent()){
            events.remove(foundEvent.get());
            logger.info("Event deleted successfully with ID: " + id);
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
            logger.info("Event updated successfully with ID: " + id);
            save();
        }
    }
}
