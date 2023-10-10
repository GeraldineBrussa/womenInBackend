package com.met.metcamp.web.womeninbackend.events.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.met.metcamp.web.womeninbackend.events.exceptions.ConversionException;
import com.met.metcamp.web.womeninbackend.events.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.met.metcamp.web.womeninbackend.events.testutils.EventTestUtils.*;
import static com.met.metcamp.web.womeninbackend.events.utils.MapperUtils.MAPPER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapperUtilsTest {
    @Mock
    private ObjectMapper objectMapper;
    private Event eventFreeMap;
    private Event eventPricedMap;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        eventFreeMap = eventToCreate();
        eventPricedMap = eventToUpdate();
    }

    @Test
    @DisplayName("Test mapToEvent - Success")
    public void testMapToEventSuccess() throws JsonProcessingException {

        when(objectMapper.readValue(eventFreeJson, Event.class)).thenReturn(eventFreeMap);

        Event result = MapperUtils.mapToEvent(eventFreeJson);

        assertEquals(eventFreeMap.getName(), result.getName());
    }

    @Test
    @DisplayName("Test mapToEventList - Success")
    public void testMapToEventListSuccess() throws JsonProcessingException {
        String eventListJson = "[" + eventFreeJson + "," + eventPricedJson + "]";
        List<Event> expectedEventList = Arrays.asList(eventFreeMap, eventPricedMap);
        when(objectMapper.readValue(eventListJson, new TypeReference<List<Event>>() {}))
                .thenReturn(expectedEventList);

        List<Event> result = MapperUtils.mapToEventList(eventListJson);
        assertEquals(expectedEventList.size(),result.size());
    }

    @Test
    @DisplayName("Test mapToJson(Event) - Success")
    public void testMapToJsonEventSuccess() {
        String result = MapperUtils.mapToJson(eventFreeMap);
        assertNotNull(result);
        assertEquals(eventFreeJson, result);
    }

    @Test
    @DisplayName("Test mapToJson(ArrayList<Event>) - Success")
    public void testMapToJsonEventListSuccess() {
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(eventFreeMap);
        eventList.add(eventPricedMap);
        String eventListJsonExpected = "[" + eventFreeJson + "," + eventPricedJson + "]";

        String result = MapperUtils.mapToJson(eventList);

        assertNotNull(result);
        assertEquals(eventListJsonExpected, result);
    }
    @Test
    @DisplayName("Test mapToEvent - JsonProcessingException")
    public void testMapToEventJsonProcessingException() throws JsonProcessingException {
        String input = "Invalid JSON";
        when(objectMapper.readValue(input, Event.class)).thenThrow(new JsonProcessingException("Error") {});

        assertThrows(ConversionException.class, () -> {
            MapperUtils.mapToEvent(input);
        });
    }
    @Test
    @DisplayName("Test mapToEventList - JSON Parsing Error")
    public void testMapToEventListJsonParsingError() throws JsonProcessingException {
        String invalidJson = "Invalid JSON";
        when(objectMapper.readValue(invalidJson, new TypeReference<List<Event>>() {}))
                .thenThrow(JsonProcessingException.class);

        assertThrows(ConversionException.class, () -> {
            MapperUtils.mapToEventList(invalidJson);
        });
    }
    @Test
    @DisplayName("Test mapToJson(Event) - JSON Processing Error")
    public void testMapToJsonEventJsonProcessingError() {
        Event invalidEvent = mock(Event.class);
        when(invalidEvent.getId()).thenThrow(new RuntimeException("Error mapping Event to JSON"));
        assertThrows(ConversionException.class, () -> {
            MapperUtils.mapToJson(invalidEvent);
        });
    }

    @Test
    @DisplayName("Test mapToJson(ArrayList<Event>) - JSON Processing Error")
    public void testMapToJsonEventListJsonProcessingError() throws JsonProcessingException {
        ArrayList<Event> invalidEventList = mock(ArrayList.class);
     when(MAPPER.writeValueAsString(invalidEventList)).thenThrow(new RuntimeException("Error mapping Event List to JSON"));
        assertThrows(ConversionException.class, () -> {
            MapperUtils.mapToJson(invalidEventList);
        });
    }
}
