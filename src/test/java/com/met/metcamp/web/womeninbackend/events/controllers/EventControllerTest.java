package com.met.metcamp.web.womeninbackend.events.controllers;

import com.met.metcamp.web.womeninbackend.events.exceptions.ApiException;
import com.met.metcamp.web.womeninbackend.events.exceptions.ConversionException;
import com.met.metcamp.web.womeninbackend.events.exceptions.RepoException;
import com.met.metcamp.web.womeninbackend.events.model.*;
import com.met.metcamp.web.womeninbackend.events.service.EventService;
import com.met.metcamp.web.womeninbackend.events.testutils.EventListProvider;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Stream;

import static com.met.metcamp.web.womeninbackend.events.testutils.EventTestUtils.*;
import static com.met.metcamp.web.womeninbackend.events.utils.MapperUtils.mapToEvent;
import static com.met.metcamp.web.womeninbackend.events.utils.MapperUtils.mapToJson;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
public class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EventService eventService;

    private final String URL = "/met/metcamp/web/womeninbackend/events";
    private final UUID nonExistentId = UUID.randomUUID();

    @Nested
    @DisplayName("Happy path methods: getAllEvents, getEventById, createEvent, updateEventById, deleteEventById - " +
            "EventController")
    class eventControllerMethodsOkTests {
        @DisplayName("getAllEvents method - EventController")
        @ParameterizedTest(name = "{0} - returns 200 or non content and the expected result")
        @ArgumentsSource(EventListProvider.class)
        void testGetAllEvents(ArrayList<Event> eventList) throws Exception {
            when(eventService.getAllEvents()).thenReturn(eventList);
            mockMvc.perform(MockMvcRequestBuilders.get(URL)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(eventList.isEmpty() ? status().isNoContent() : status().isOk())
                    .andExpect(eventList.isEmpty() ? MockMvcResultMatchers.content().string("") :
                            MockMvcResultMatchers.content().json(String.format("{\"events\":%s}",
                                    mapToJson(eventList))));
        }
        @Test
        @DisplayName("getEventById method- returns 200, the expected event name and calls EventService - " +
                "EventController")
        public void testGetEventById () throws Exception {
            when(eventService.getEventById(eventFree.getId())).thenReturn(eventFree);

            mockMvc.perform(MockMvcRequestBuilders
                            .get(String.format("%s/%s", URL, eventFree.getId()))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.event.name").value(eventFree.getName()));
            verify(eventService, times(1)).getEventById(any(UUID.class));
        }
        @DisplayName("createEvent method - EventController")
        @ParameterizedTest(name = "{0} - returns 201, the expected create event and calls EventService")
        @MethodSource("inputForCreateEvent")
        void testCreateEvent(String body) throws Exception {
            Event inputEvent = mapToEvent(body);
            Event expectedEvent = mapToEvent(body);
            expectedEvent.setId(UUID.randomUUID());
            when(eventService.createEvent(any(Event.class))).thenReturn(expectedEvent);

            mockMvc.perform(MockMvcRequestBuilders
                    .post(URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                    .andExpect(status().isCreated())
                    .andExpect(MockMvcResultMatchers.content().json(String.format("{\"event\":%s}",
                            mapToJson(expectedEvent))));

            ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
            verify(eventService).createEvent(eventCaptor.capture());
            assertThat(eventCaptor.getValue()).usingRecursiveComparison().isEqualTo(inputEvent);
        }
        @Test
        @DisplayName("updateEventById - returns 200, the expected event id, the expected event name updated and calls" +
                " EventService - EventController")
        public void testUpdateEventById () throws Exception {
            Event updatedEvent = eventToUpdate();
            updatedEvent.setId(eventFree.getId());

            when(eventService.updateEvent(eq(eventFree.getId()), any(Event.class))).thenReturn(updatedEvent);

            mockMvc.perform(MockMvcRequestBuilders
                            .put(String.format("%s/%s", URL, eventFree.getId()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(updatedEvent)))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.event.id").value(eventFree.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.event.name").value(updatedEvent.getName()));
            verify(eventService, times(1)).updateEvent(eq(eventFree.getId()), any(Event.class));
        }
        @Test
        @DisplayName("deleteEventById - returns no content, message and calls EventService - EventController")
        public void testDeleteEventById () throws Exception {
            mockMvc.perform(MockMvcRequestBuilders
                            .delete(String.format("%s/%s", URL, eventFree.getId()))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent())
                    .andExpect(header().string("X-Message", String.format("Event with ID %s successfully deleted",
                            eventFree.getId())));
            verify(eventService, times(1)).deleteEvent(eq(eventFree.getId()));
        }
        static Stream<Arguments> inputForCreateEvent() {
            String eventListPricesJson =String.format( "{\"type\":\"ANIVERSARIO\"," +
                            "\"name\":\"Gala Met\",\"attendees\":150,\"organizer\":\"MeT\"," +
                            "\"prices\":[{\"type\":\"REGULAR_ONE_DAY\",\"currency\":\"ARS\",\"value\":7000},{\"type\":\"VIP_ONE_DAY\",\"currency\":\"ARS\",\"value\":3500.0}],\"start_date\":\"%s\",\"end_date\":\"%s\"}",
                    LocalDateTime.now().plusMinutes(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    LocalDateTime.now().plusMinutes(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            return java.util.stream.Stream.of(
                    Arguments.of(Named.of("Event without price", eventFreeJson)),
                    Arguments.of(Named.of("Event with a list of 1 price", eventPricedJson)),
                    Arguments.of(Named.of("Event with a list of 2 prices",eventListPricesJson)));
        }
    }
    @Nested
    @DisplayName("Exceptions - EventController")
    class eventControllerExceptionsTests {
        @Test
        @DisplayName("getEventById - throws ApiException when event is not found - returns 404 and message")
        void testGetEventByIdNotFound() throws Exception {
            doThrow(new ApiException(404, String.format("Event %s doesn't exist", nonExistentId)))
                    .when(eventService).getEventById(nonExistentId);
            mockMvc.perform(MockMvcRequestBuilders
                            .get(String.format("%s/%s", URL, nonExistentId))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Event " + nonExistentId + " doesn't exist\"}"));
        }
        @DisplayName("createEvent - in valid input throws MethodArgumentNotValidException")
        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("inputForEventsBadRequest")
        void testCreateEventBadRequest( String invalidEvent, String[] expectedErrors) throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(invalidEvent))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.errors[*]").value(containsInAnyOrder(expectedErrors)));
        }
        @Test
        @DisplayName("updateEventById - throws ApiException when event is not found - returns 404 and message")
        void testUpdateEventByIdNotFound() throws Exception {
            doThrow(new ApiException(404, String.format("Event %s doesn't exist", nonExistentId)))
                    .when(eventService).updateEvent(eq(nonExistentId), any(Event.class));

            mockMvc.perform(MockMvcRequestBuilders
                            .put(String.format("%s/%s", URL, nonExistentId))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(eventPriced)))
                    .andExpect(status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Event " + nonExistentId + " doesn't exist\"}"));
        }
        @DisplayName("updateEventById - in valid input throws MethodArgumentNotValidException")
        @ParameterizedTest(name = "{index} {1}")
        @MethodSource("inputForEventsBadRequest")
        void testUpdateEventByIdBadRequest(String body, String[] expectedErrors) throws Exception {

            mockMvc.perform(MockMvcRequestBuilders
                    .put(String.format("%s/%s", URL, eventFree.getId()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                    .andExpect(status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.errors[*]").value(containsInAnyOrder(expectedErrors)));
        }
        @Test
        @DisplayName("deleteEventById - throws ApiException when event is not found - returns 404 and message")
        void testDeleteEventByIdNotFound() throws Exception {
            doThrow(new ApiException(404, String.format("Event %s doesn't exist", nonExistentId)))
                    .when(eventService).deleteEvent(nonExistentId);
            mockMvc.perform(MockMvcRequestBuilders
                            .delete(String.format("%s/%s", URL, nonExistentId))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Event " + nonExistentId + " doesn't exist\"}"));
        }
        @DisplayName("error Handling - returns 500 and message expected")
        @ParameterizedTest(name = "{index} exception {0}")
        @MethodSource("exceptionHandlingInput")
        void testErrorHandler(RuntimeException e, String message) throws Exception {
            when(eventService.getEventById(nonExistentId)).thenThrow(e);

            mockMvc.perform(MockMvcRequestBuilders
                            .get(String.format("%s/%s", URL, nonExistentId))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError())
                    .andExpect(MockMvcResultMatchers.content().json(String.format("{\"message\":\"%s\"}", message)));
        }

        static Stream<Arguments> inputForEventsBadRequest() {
            LocalDateTime startDate = LocalDateTime.now().plusMinutes(1);
            String startDateFormatted = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String endDateFormatted = LocalDateTime.now().plusMinutes(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String validFreeEvent = "{\"type\":\"ANIVERSARIO\",\"name\":\"Gala MeT\",\"attendees\":140," +
                    "\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}";
            String validBaseEvent = "{\"type\":\"ANIVERSARIO\",\"name\":\"Gala MeT\",\"attendees\":140," +
                    "\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\",\"prices\":[%s]}";
            return Stream.of(
                    //all validations
                    Arguments.of("{}",new String[]{"Start date is required","Name is required","Organizer is " +
                            "required","Invalid enum value, must be any of: ANIVERSARIO, CLASE_METCAMP, ENCUENTRO_METLAB","Attendees is required","End date is required"}),
                    //dates
                    Arguments.of(String.format(validFreeEvent,
                            startDate.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), endDateFormatted),new String[]{ "endDate: Start date must be before end date"}),
                    Arguments.of(String.format(validFreeEvent,
                            startDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                            endDateFormatted), new String[]{"Start date must be in the future"}),
                    Arguments.of(String.format("{\"type\":\"ANIVERSARIO\",\"name\":\"Gala MeT\",\"attendees\":140," +
                                    "\"organizer\":\"MeT\",\"end_date\":\"%s\"}",  endDateFormatted),
                    new String[]{ "Start date is required"}),
                    Arguments.of(String.format("{\"type\":\"ANIVERSARIO\",\"name\":\"Gala MeT\",\"attendees\":140," +
                                    "\"organizer\":\"MeT\",\"start_date\":\"%s\"}", startDateFormatted),
                    new String[]{ "End date is required"}),
                    Arguments.of(String.format("{\"name\":\"Gala MeT\",\"attendees\":140,\"organizer\":\"MeT\"," +
                            "\"start_date\":\"%s\",\"end_date\":\"%s\"}", startDateFormatted, endDateFormatted), new String[]{"Invalid enum value, must be any of: ANIVERSARIO, CLASE_METCAMP, ENCUENTRO_METLAB"}),
                    Arguments.of(String.format("{\"type\":\"something\",\"name\":\"Gala MeT\",\"attendees\":140," +
                            "\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", startDateFormatted, endDateFormatted), new String[]{"Invalid enum value, must be any of: ANIVERSARIO, CLASE_METCAMP, ENCUENTRO_METLAB"}),
                    Arguments.of(String.format("{\"type\":\"\",\"name\":\"Gala MeT\",\"attendees\":140," +
                            "\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", startDateFormatted, endDateFormatted), new String[]{"Invalid enum value, must be any of: ANIVERSARIO, CLASE_METCAMP, ENCUENTRO_METLAB"}),
                    //name
                    Arguments.of(String.format("{\"type\":\"CLASE_METCAMP\",\"attendees\":140,\"organizer\":\"MeT\"," +
                            "\"start_date\":\"%s\",\"end_date\":\"%s\"}", startDateFormatted, endDateFormatted),
                            new String[]{ "Name is required"}),
                    Arguments.of(String.format("{\"type\":\"CLASE_METCAMP\",\"name\":\"a\",\"attendees\":140," +
                            "\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", startDateFormatted, endDateFormatted),new String[]{ "Name is too short"}),
                    //attendees
                    Arguments.of(String.format("{\"type\":\"ENCUENTRO_METLAB\",\"name\":\"Gala MeT\"," +
                    "\"attendees\":0," +
                            "\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", startDateFormatted,
                    endDateFormatted), new String[]{"The number of event attendees must be greater than 0"}),
                    Arguments.of(String.format("{\"type\":\"ENCUENTRO_METLAB\",\"name\":\"Gala MeT\"," +
                            "\"attendees\":-10,\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}",
                            startDateFormatted, endDateFormatted), new String[]{ "The number of event attendees must " +
                            "be greater than 0"}),
                    Arguments.of(String.format("{\"type\":\"ENCUENTRO_METLAB\",\"name\":\"Gala MeT\"," +
                                    "\"attendees\":200,\"organizer\":\"MeT\",\"start_date\":\"%s\"," +
                                    "\"end_date\":\"%s\"}",
                            startDateFormatted, endDateFormatted), new String[]{ "The number of attendees to the event must be less than or equal to 150"}),
                    Arguments.of(String.format("{\"type\":\"ENCUENTRO_METLAB\",\"name\":\"Gala MeT\"," +
                            "\"organizer\":\"MeT\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", startDateFormatted, endDateFormatted), new String[]{"Attendees is required"}),
                    //organizer
                    Arguments.of(String.format("{\"type\":\"ANIVERSARIO\",\"name\":\"Gala MeT\",\"attendees\":140," +
                            "\"organizer\":\"a\",\"start_date\":\"%s\",\"end_date\":\"%s\"}", startDateFormatted,
                            endDateFormatted), new String[]{"Organizer name is too short"}),
                    Arguments.of(String.format("{\"type\":\"ANIVERSARIO\",\"name\":\"Gala MeT\",\"attendees\":150," +
                            "\"start_date\":\"%s\",\"end_date\":\"%s\"}", startDateFormatted, endDateFormatted), new String[]{"Organizer is required"}),

                    //TicketType
                    Arguments.of(String.format(validBaseEvent, startDateFormatted, endDateFormatted, "{\"currency" +
                            "\":\"ARS\",\"value\":2500.0}"), new String[]{ "Invalid ticket type, must be any of: REGULAR_FULL_PASS, REGULAR_ONE_DAY, VIP_FULL_PASS, VIP_ONE_DAY"}),
                    Arguments.of(String.format(validBaseEvent, startDateFormatted, endDateFormatted, "{\"type" +
                            "\":\"other\",\"currency\":\"ARS\",\"value\":2500.0}"), new String[]{"Invalid ticket type, must be any of: REGULAR_FULL_PASS, REGULAR_ONE_DAY, VIP_FULL_PASS, VIP_ONE_DAY"}),
                    Arguments.of(String.format(validBaseEvent, startDateFormatted, endDateFormatted, "{\"type\":\"\"," +
                            "\"currency\":\"ARS\",\"value\":2500.0}"), new String[]{"Invalid ticket type, must be any of: REGULAR_FULL_PASS, REGULAR_ONE_DAY, VIP_FULL_PASS, VIP_ONE_DAY"}),
                    //currency
                    Arguments.of(String.format(validBaseEvent, startDateFormatted, endDateFormatted, "{\"type" +
                            "\":\"REGULAR_FULL_PASS\",\"value\":2500.0}"), new String[]{"Invalid currency, must be any of: ARS, CLP, COP, USD"}),
                    Arguments.of(String.format(validBaseEvent, startDateFormatted, endDateFormatted, "{\"type" +
                            "\":\"REGULAR_FULL_PASS\",\"currency\":\"\",\"value\":2500.0}"), new String[]{"Invalid currency, must be any of: ARS, CLP, COP, USD"}),
                    Arguments.of(String.format(validBaseEvent, startDateFormatted, endDateFormatted, "{\"type" +
                            "\":\"REGULAR_ONE_DAY\",\"currency\":\"other\",\"value\":2500.0}"), new String[]{
                                    "Invalid currency, must be any of: ARS, CLP, COP, USD"}),
                    //value
                    Arguments.of(String.format(validBaseEvent, startDateFormatted, endDateFormatted, "{\"type" +
                            "\":\"REGULAR_ONE_DAY\",\"currency\":\"ARS\"}"), new String[]{ "Price value is required"}),
                    Arguments.of(String.format(validBaseEvent, startDateFormatted, endDateFormatted, "{\"type" +
                            "\":\"VIP_FULL_PASS\",\"currency\":\"ARS\",\"value\":0}"), new String[]{"Price value must be greater than zero"}),
                    Arguments.of(String.format(validBaseEvent, startDateFormatted, endDateFormatted, "{\"type" +
                            "\":\"VIP_FULL_PASS\",\"currency\":\"COP\",\"value\":0.00}"), new String[]{"Price value " +
                            "must be greater than zero"}),
                    Arguments.of(String.format(validBaseEvent, startDateFormatted, endDateFormatted, "{\"type" +
                            "\":\"VIP_ONE_DAY\",\"currency\":\"CLP\",\"value\":-10.0}"), new String[]{"Price value must be greater than zero"}),
                    Arguments.of(String.format(validBaseEvent, startDateFormatted, endDateFormatted, "{\"type" +
                            "\":\"VIP_ONE_DAY\",\"currency\":\"USD\",\"value\":-2}"), new String[]{"Price value must " +
                            "be greater than zero"}),

                    //duplicate price
                    Arguments.of(String.format(validBaseEvent, startDateFormatted, endDateFormatted, "{\"type" +
                            "\":\"VIP_ONE_DAY\",\"currency\":\"USD\",\"value\":2},{\"type\":\"VIP_ONE_DAY\",\"currency\":\"USD\",\"value\":5}"), new String[]{"Multiple prices " +
                            "for same ticket type are not allowed"})
            );
        }
        static Stream<Arguments> exceptionHandlingInput() {
            return Stream.of(
                    Arguments.of(new ConversionException(new RuntimeException()), "Internal error"),
                    Arguments.of(new RepoException("ERROR"), "Internal error"),
                    Arguments.of(new RuntimeException(), "Generic error")
            );
        }
    }
}
