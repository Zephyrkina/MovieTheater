package ua.com.spring.core.training.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import ua.com.spring.core.training.dao.EventRepository;
import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.EventRating;
import ua.com.spring.core.training.exceptions.EventNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = {"classpath:application-test-context.xml"})
class DefaultEventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private DefaultEventService eventService;

    @Test
    void whenGetByExistingName_shouldReturnEvent() {
        String existingName = "Event1";
        Event existingEvent = new Event("Event1", 10.0, EventRating.MID );

        when(eventRepository.getByName(existingName)).thenReturn(Optional.of(existingEvent));
        Event actualEvent = eventService.getByName(existingName);

        assertThat(actualEvent, equalTo(existingEvent));
        verify(eventRepository).getByName(existingName);
    }

    @Test
    void givenNonExistingName_whenGetByName_shouldThrowException() {
        String nonExistingName = "Event1";

        when(eventRepository.getByName(nonExistingName)).thenReturn(Optional.empty());
        assertThrows(EventNotFoundException.class, () -> {eventService.getByName(nonExistingName);});

        verify(eventRepository).getByName(nonExistingName);
    }

    @Test
    void givenNullName_whenGetByName_shouldThrowException() {
        String nullName = null;

        assertThrows(IllegalArgumentException.class, () -> {eventService.getByName(nullName);});

        verify(eventRepository, never()).getByName(nullName);
    }

    @Test
    void givenEmptyName_whenGetByName_shouldThrowException() {
        String emptyName = "";

        assertThrows(IllegalArgumentException.class, () -> {eventService.getByName(emptyName);});

        verify(eventRepository, never()).getByName(emptyName);

    }

    @Test
    void givenValidEvent_whenSave_thenShouldSave() {
        Event expectedEvent = new Event("Event1", 10.0, EventRating.MID );

        when(eventRepository.save(expectedEvent)).thenReturn(expectedEvent);
        Event actualEvent = eventRepository.save(expectedEvent);

        assertThat(actualEvent, equalTo(expectedEvent));
        verify(eventRepository).save(expectedEvent);
    }

    @Test
    void givenNullEvent_whenSave_thenShouldThrowException() {
        Event expectedEvent = null;

        assertThrows(IllegalArgumentException.class, () -> {eventService.save(expectedEvent);});

        verify(eventRepository, never()).save(expectedEvent);
    }

    @Test
    void givenValidEvent_whenRemove_thenShouldRemove() {
        Event expectedEvent = new Event("Event1", 10.0, EventRating.MID );

        when(eventRepository.remove(expectedEvent)).thenReturn(expectedEvent);
        Event actualEvent = eventRepository.remove(expectedEvent);

        assertThat(actualEvent, equalTo(expectedEvent));
        verify(eventRepository).remove(expectedEvent);
    }

    @Test
    void givenNullEvent_whenRemove_thenShouldThrowException() {
        Event expectedEvent = null;

        assertThrows(IllegalArgumentException.class, () -> {eventService.remove(expectedEvent);});

        verify(eventRepository, never()).remove(expectedEvent);
    }

    @Test
    void givenExistingId_whenGetById_shouldReturnEvent() {
        Long existingId = 2L;
        Event existingEvent = new Event("Event1", 10.0, EventRating.MID );

        when(eventRepository.getById(existingId)).thenReturn(Optional.of(existingEvent));
        Event actualEvent = eventService.getById(existingId);

        assertThat(actualEvent, equalTo(existingEvent));
        verify(eventRepository).getById(existingId);
    }

    @Test
    void givenNonExistingId_whenGetById_shouldThrowException() {
        Long nonExistingId = 223L;

        when(eventRepository.getById(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(EventNotFoundException.class, () -> {eventService.getById(nonExistingId);});

        verify(eventRepository).getById(nonExistingId);
    }

    @Test
    void givenNullId_whenGetById_shouldThrowException() {
        Long nullId = null;

        assertThrows(IllegalArgumentException.class, () -> {eventService.getById(nullId);});

        verify(eventRepository, never()).getById(nullId);
    }

    @Test
    void whenGetAll () {
        int expectedEventsSize = 3;
        List<Event> expectedEvents = createEvents(expectedEventsSize);

        when(eventRepository.getAll()).thenReturn(expectedEvents);

        List<Event> actualEvents = eventRepository.getAll();

        assertThat(actualEvents, equalTo(expectedEvents));
        assertThat(actualEvents.size(), equalTo(expectedEventsSize));
        verify(eventRepository).getAll();
    }

    private List<Event> createEvents(int eventQuantity) {
        List<Event> eventList = new ArrayList<>();

        for (int i = 0; i < eventQuantity; i++) {
            Event event = new Event("Event"+i, i*10.0+i, EventRating.MID);
            eventList.add(event);
        }
        return eventList;
    }
}