package com.met.metcamp.web.womeninbackend.events.testutils;

import com.met.metcamp.web.womeninbackend.events.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventTestUtils {
    public static final Event eventFree = new Event(UUID.fromString("11f772c6-d8ea-4ea6-956b-e5d4c9882e54"),
            EventType.CLASE_METCAMP, "Encuentro 17", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5)
            , 20, "MetLabWeb", List.of());
    public static final Event eventPriced = new Event(UUID.fromString("21f772c6-d8ea-4ea6-956b-e5d4c9882e54"),
            EventType.ENCUENTRO_METLAB, "Encuentro METLAB",
            LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5), 35, "MetLabWeb",
            List.of(new Price(TicketType.REGULAR_FULL_PASS, Currency.ARS, 100.25)));
    public static final String eventFreeJson = String.format(
            "{\"type\":\"CLASE_METCAMP\"," +
                    "\"name\":\"Encuentro 17\",\"attendees\":20," +
                    "\"organizer\":\"MetLabWeb\",\"start_date\":\"%s\",\"end_date\":\"%s\"}",
            eventFree.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            eventFree.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    );
    public static final String eventPricedJson =String.format( "{\"type\":\"ENCUENTRO_METLAB\"," +
                    "\"name\":\"Encuentro METLAB\",\"attendees\":35,\"organizer\":\"MetLabWeb\"," +
                    "\"prices\":[{\"type\":\"REGULAR_FULL_PASS\",\"currency\":\"ARS\",\"value\":100.25}],\"start_date\":\"%s\",\"end_date\":\"%s\"}",
            eventFree.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            eventFree.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));


    public static Event eventToCreate() {
        Event event = new Event();
        event.setType(eventFree.getType());
        event.setName(eventFree.getName());
        event.setAttendees(eventFree.getAttendees());
        event.setEndDate(eventFree.getEndDate());
        event.setOrganizer(eventFree.getOrganizer());
        event.setStartDate(eventFree.getStartDate());
        return event;
    }

    public static Event eventToUpdate() {
        Event event = new Event();
        event.setType(eventPriced.getType());
        event.setName(eventPriced.getName());
        event.setAttendees(eventPriced.getAttendees());
        event.setOrganizer(eventPriced.getOrganizer());
        event.setPrices(eventPriced.getPrices());
        event.setStartDate(eventPriced.getStartDate());
        event.setEndDate(eventPriced.getEndDate());
        return event;
    }
}
