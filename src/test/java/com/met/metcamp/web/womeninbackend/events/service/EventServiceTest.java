package com.met.metcamp.web.womeninbackend.events.service;

import com.met.metcamp.web.womeninbackend.events.exceptions.ApiException;
import com.met.metcamp.web.womeninbackend.events.model.*;
import com.met.metcamp.web.womeninbackend.events.repository.EventRepository;
import com.met.metcamp.web.womeninbackend.events.testutils.EventListProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static com.met.metcamp.web.womeninbackend.events.testutils.EventTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @InjectMocks
    private EventService eventService;
    @Mock
    private EventRepository eventRepository;
    private UUID nonExistentId;

    @DisplayName("Tests for getAllEvents method - non-null list and calls EventRepository - EventService")
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(EventListProvider.class)
    void testGetAllEvents(ArrayList<Event> events) {
        when(eventRepository.getEvents()).thenReturn(events);
        assertEquals(events, eventService.getAllEvents());
        assertNotNull(events);
        verify(eventRepository, times(1)).getEvents();
    }
    @Test
    @DisplayName("Test for createEvent method - returns a non-null created event and calls Repository - EventService")
    public void testCreateEventNotNull() {
        Event createdEvent = eventService.createEvent(eventFree);
        assertNotNull(createdEvent);
        assertEquals(eventFree, createdEvent);
        verify(eventRepository, times(1)).add(eventFree);
    }
    @Nested
    @DisplayName("Happy path methods: getEventById, updateEvent, deleteEvent - EventService")
    class eventServiceMethodsOkTests {
        @BeforeEach
        void setUp(){
            when(eventRepository.find(eventFree.getId())).thenReturn(Optional.of(eventFree));
        }
        @Test
        @DisplayName("Method getEventById - returns the event found, not null and calls EventRepository")
        public void testGetEventById() {
            Event foundEvent = eventService.getEventById(eventFree.getId());
            assertNotNull(foundEvent);
            assertEquals(eventFree.getId(), foundEvent.getId());
            verify(eventRepository, times(1)).find(eventFree.getId());
        }
        @Test
        @DisplayName("Method updateEvent - returns the updated event, non null and calls EventRepository")
        public void testUpdateEvent() {
            Event updateEvent = eventService.updateEvent(eventFree.getId(), eventPriced);
            assertEquals(eventPriced.getName(), updateEvent.getName());
            assertNotNull(updateEvent);
            verify(eventRepository, times(1)).find(eventFree.getId());
            verify(eventRepository, times(1)).update(eventFree.getId(),eventPriced);
        }
        @Test
        @DisplayName("Method deleteEvent - calls EventRepository")
        public void testDeleteEvent() {
            assertDoesNotThrow(() -> eventService.deleteEvent(eventFree.getId()));
            verify(eventRepository, times(1)).delete(eventFree.getId());
        }
    }

    @Nested
    @DisplayName("Exceptions - EventService")
    class eventServiceExceptionsTests {
        @BeforeEach
        void setUp(){
            nonExistentId = UUID.randomUUID();
            when(eventRepository.find(nonExistentId)).thenReturn(Optional.empty());
        }
        @Test
        @DisplayName("Method getEventById - throws ApiException when event is not found")
        public void testGetEventByIdThrowsApiException() {
            ApiException exception =assertThrows(ApiException.class, () -> {
                eventService.getEventById(nonExistentId);
            });
            assertEquals(404, exception.getStatus());
            assertEquals("Event with ID " + nonExistentId +" not found",
                    exception.getMessage());
        }
        @Test
        @DisplayName("Method updateEvent - throws ApiException when event is not found")
        public void testUpdateEventThrowsApiException() {
            ApiException exception =assertThrows(ApiException.class, () -> {
                eventService.updateEvent(nonExistentId, any(Event.class));
            });
            assertEquals(404, exception.getStatus());
            assertEquals("Event with ID " + nonExistentId +" not found",
                    exception.getMessage());
            verify(eventRepository, never()).update(Mockito.any(UUID.class), Mockito.any(Event.class));
        }
        @Test
        @DisplayName("Method deleteEvent - throws ApiException when event is not found")
        public void testDeleteEventThrowsApiException() {
            ApiException exception =assertThrows(ApiException.class, () -> {
                eventService.deleteEvent(nonExistentId);
            });
            assertEquals(404, exception.getStatus());
            assertEquals("Event with ID " + nonExistentId +" not found",
                    exception.getMessage());
            verify(eventRepository, never()).delete(Mockito.any(UUID.class));
        }
    }
}