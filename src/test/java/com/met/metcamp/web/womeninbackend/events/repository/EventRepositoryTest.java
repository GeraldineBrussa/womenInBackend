package com.met.metcamp.web.womeninbackend.events.repository;

import com.met.metcamp.web.womeninbackend.events.exceptions.ConversionException;
import com.met.metcamp.web.womeninbackend.events.exceptions.RepoException;
import com.met.metcamp.web.womeninbackend.events.model.Event;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.nio.file.Path;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static com.met.metcamp.web.womeninbackend.events.testutils.EventTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
public class EventRepositoryTest {
    private static final Path PATH_OK = Path.of("src/test/resources/repository/events.json");
    private static final Path PATH_MALFORMED = Path.of("src/test/resources/repository/malformed.json");
    private static final Path PATH_NON_EXISTING = Path.of("");
    private Event newEventRepo;
    private EventRepository repository;
    @Test
    @DisplayName("loadEvents returns empty list")
    void testLoadEventsEmpty() {
        try (MockedStatic<Path> pathMocked = mockStatic(Path.class)) {
            pathMocked.when((MockedStatic.Verification) Path.of(anyString())).thenReturn(PATH_OK);

            EventRepository repository = assertDoesNotThrow(EventRepository::new);

            assertThat(repository.getEvents()).isEmpty();
        }
    }
    @Nested
    @DisplayName("Testing happy path - EventRepository")
    public class EventRepositoryTestOK {
        @BeforeEach
        void setUp() {
            try (MockedStatic<Path> pathMocked = mockStatic(Path.class)) {
                pathMocked.when((MockedStatic.Verification) Path.of(anyString())).thenReturn(PATH_OK);
            }
            repository =new EventRepository();
            repository.getEvents().clear();
            newEventRepo = eventToCreate();
            repository.add(newEventRepo);
        }
        @AfterEach
        void tearDown() {repository.getEvents().clear();}
        @Test
        @DisplayName("add and find event successfully")
        void testAddAndFindOk() {
                Optional<Event> addedEvent = repository.find(newEventRepo.getId());
                assertTrue(addedEvent.isPresent());
                assertEquals(newEventRepo, addedEvent.get());
        }
        @Test
        @DisplayName("loadEvents returns a list with 1 event")
        void testLoadEventsOk() {
                assertThat(repository.getEvents())
                        .isNotEmpty()
                        .satisfiesExactly(e -> {
                            assertEquals(newEventRepo.getId(), e.getId());
                            assertEquals(newEventRepo.getName(), e.getName());
                            assertEquals(newEventRepo.getType(), e.getType());
                            assertEquals(newEventRepo.getOrganizer(), e.getOrganizer());
                            assertEquals(newEventRepo.getAttendees(), e.getAttendees());
                            assertEquals(newEventRepo.getStartDate().truncatedTo(ChronoUnit.SECONDS),
                                    e.getStartDate().truncatedTo(ChronoUnit.SECONDS));
                            assertEquals(newEventRepo.getEndDate().truncatedTo(ChronoUnit.SECONDS),
                                    e.getEndDate().truncatedTo(ChronoUnit.SECONDS));
                            assertNull(e.getPrices());
                        });
        }
        @Test
        @DisplayName("update event successfully")
        void testUpdateOk() {
                Event updateRepo = eventToUpdate();

                repository.update(newEventRepo.getId(), updateRepo);

                assertThat(repository.getEvents())
                        .isNotEmpty()
                        .satisfiesExactly(e -> {
                            assertEquals(newEventRepo.getId(), e.getId());
                            assertEquals(updateRepo.getName(), e.getName());
                            assertEquals(updateRepo.getType(), e.getType());
                            assertEquals(updateRepo.getOrganizer(), e.getOrganizer());
                            assertEquals(updateRepo.getAttendees(), e.getAttendees());
                            assertEquals(updateRepo.getStartDate().truncatedTo(ChronoUnit.SECONDS), e.getStartDate().truncatedTo(ChronoUnit.SECONDS));
                            assertEquals(updateRepo.getEndDate().truncatedTo(ChronoUnit.SECONDS), e.getEndDate().truncatedTo(ChronoUnit.SECONDS));
                            assertEquals(1, e.getPrices().size());
                            assertEquals(updateRepo.getPrices().get(0).getType(), e.getPrices().get(0).getType());
                            assertEquals(updateRepo.getPrices().get(0).getCurrency(), e.getPrices().get(0).getCurrency());
                            assertEquals(updateRepo.getPrices().get(0).getValue(), e.getPrices().get(0).getValue());
                        });
        }
        @Test
        @DisplayName("delete event successfully")
        void testDeleteOk() {
                repository.delete(newEventRepo.getId());
                assertThat(repository.getEvents()).isEmpty();
        }
    }
    @Nested
    @DisplayName("Testing Exceptions - EventRepository")
    class EventRepositoryTestRepoExceptions {
        @Test
        @DisplayName("non existing file - reading error")
        void testNonExistingFileReadingError() {
            try (MockedStatic<Path> pathMocked = mockStatic(Path.class)) {
                pathMocked.when((MockedStatic.Verification) Path.of(anyString()))
                        .thenReturn(PATH_NON_EXISTING);

                RepoException readingException = assertThrows(RepoException.class, EventRepository::new);
                assertEquals("Error reading file", readingException.getMessage());
            }
        }
        @Test
        @DisplayName("non existing file - writing error")
        void testNonExistingFileWritingError() {
            try (MockedStatic<Path> pathMocked = mockStatic(Path.class)) {
                pathMocked.when((MockedStatic.Verification) Path.of(anyString()))
                        .thenReturn(PATH_OK)
                        .thenReturn(PATH_NON_EXISTING);

                EventRepository repository = assertDoesNotThrow(EventRepository::new);

                RepoException savingException = assertThrows(RepoException.class, () -> repository.add(eventPriced));
                assertEquals("Error writing file", savingException.getMessage());
            }
        }
        @Test
        @DisplayName("malformed file")
        void testMalformedFile() {
            try (MockedStatic<Path> pathMocked = mockStatic(Path.class)) {
                pathMocked.when((MockedStatic.Verification) Path.of(anyString()))
                        .thenReturn(PATH_MALFORMED);
                assertThrows(ConversionException.class, EventRepository::new);
            }
        }
    }
}

