package com.met.metcamp.web.womeninbackend.events.repository;

import com.met.metcamp.web.womeninbackend.events.exceptions.RepoException;
import com.met.metcamp.web.womeninbackend.events.model.Event;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static com.met.metcamp.web.womeninbackend.events.utils.MapperUtils.mapToEventList;
import static com.met.metcamp.web.womeninbackend.events.utils.MapperUtils.mapToJson;

@Repository
public class EventRepository {
    private static final Logger logger = LogManager.getLogger(EventRepository.class);
    @Getter
    private final ArrayList<Event> events;

    public EventRepository(){
        this.events = loadEvents();
        logger.info("EventRepository initialized");
    }

    private ArrayList<Event> loadEvents() {
        try{
            byte[] bytes = Files.readAllBytes(Path.of("src/main/resources/repository/events.json"));
            String input = new String(bytes);
            logger.info("Loaded events from file successfully");
            return mapToEventList(input);
        }catch (IOException io){
            logger.error("Error reading file" + io.getMessage(), io);
            throw new RepoException("Error reading file");
        }
    }
    private void save(){
        try {
            String data = mapToJson(events);
            Files.writeString(Paths.get("src/main/resources/repository/events.json"), data);
            logger.info("Saved events to file successfully");
        } catch (IOException io){
            logger.error("Error writing file: " + io.getMessage(), io);
            throw new RepoException("Error writing file");
        }
    }
    public Optional<Event> find (UUID id){
        return events.stream()
                .filter(event -> event.getId().equals(id))
                .findFirst();
    }
    public void add (Event newEvent){
        UUID uniqueId = generateUniqueId();
        newEvent.setId(uniqueId);
        events.add(newEvent);
        save();
    }
    public void delete (UUID id){
        find(id).ifPresent(event -> {
            events.remove(event);
            save();
        });
    }
    public void update(UUID id, Event updateInfo){
        find(id).ifPresent(foundEvent -> {
            events.remove(foundEvent);
            foundEvent.update(updateInfo);
            events.add(foundEvent);
            save();
        });
    }
    private UUID generateUniqueId() {
        while (true) {
            UUID eventId = UUID.randomUUID();
            if (find(eventId).isEmpty()) {
                return eventId;
            }
        }
    }
}
