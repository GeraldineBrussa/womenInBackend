package com.met.metcamp.web.womeninbackend.events.testutils;

import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.met.metcamp.web.womeninbackend.events.testutils.EventTestUtils.eventFree;
import static com.met.metcamp.web.womeninbackend.events.testutils.EventTestUtils.eventPriced;

public class EventListProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(Named.of("Return an empty list", new ArrayList<>())),
                Arguments.of(Named.of("Returns a list with 1 event", new ArrayList<>(List.of(eventFree)))),
                Arguments.of(Named.of("Returns a list with 2 events", new ArrayList<>(List.of(eventFree, eventPriced))))
        );
    }
}
