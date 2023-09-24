package com.met.metcamp.web.womeninbackend.events.repository;

import com.met.metcamp.web.womeninbackend.events.exceptions.RepoException;
import com.met.metcamp.web.womeninbackend.events.model.Event;
import com.met.metcamp.web.womeninbackend.events.utils.MapperUtils;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
            logger.error("Error reading file" + io.getMessage(), io);
            throw new RepoException("Error reading file");
        }
    }
    private void save(){
        try {
            String data = mapperUtils.mapToJson(events);
            Files.writeString(Paths.get("src/main/resources/repository/events.json"), data);
            logger.info("Saved events to file successfully");
        } catch (IOException io){
            logger.error("Error writing file: " + io.getMessage(), io);
            throw new RepoException("Error writing file");
        }
    }
    public Optional<Event> find (int id){
        return events.stream()
                .filter(event -> event.getId() == id)
                .findFirst();
    }
    public void add (Event newEvent){
        events.add(newEvent);
        save();
    }
    public void delete (int id){
        find(id).ifPresent(event -> {
            events.remove(event);
            save();
        });
    }
    public void update(int id, Event updateInfo){
        find(id).ifPresent(foundEvent -> {
            events.remove(foundEvent);
            foundEvent.update(updateInfo);
            events.add(foundEvent);
            save();
        });
    }
}
