package ua.com.spring.core.training.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ua.com.spring.core.training.dao.EventRepository;
import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.EventRating;
import ua.com.spring.core.training.exceptions.EventNotFoundException;
import ua.com.spring.core.training.service.EventService;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultEventServiceTestIT {

    private EventService eventService;

    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        eventRepository = new EventRepository();
        eventService = new DefaultEventService(eventRepository);
    }

    @Test
    void whenSave_thenShouldSave() {
        Event expectedEvent = new Event("Event1", 10.0, EventRating.MID);
        Event actualEvent = eventService.save(expectedEvent);

        assertThat(eventService.getByName("Event1"), equalTo(expectedEvent));
    }

    @Test
    void whenGetByName_thenShouldReturnEvent() {
        String expectedName = "Event1";
        Event expectedEvent = new Event(expectedName, 10.0, EventRating.MID);
        eventService.save(expectedEvent);

        Event actualEvent = eventService.getByName(expectedName);

        assertThat(actualEvent, equalTo(expectedEvent));
    }

    @Test
    void whenRemove_thenShouldRemove() {
        String expectedName = "Event1";
        Event expectedEvent = new Event(expectedName, 10.0, EventRating.MID);
        eventService.save(expectedEvent);

        assertThat(eventService.getByName(expectedName), is(notNullValue()));

        eventService.remove(expectedEvent);

        assertThrows(EventNotFoundException.class, () -> eventService.getByName(expectedName));
    }

    @Test
    void whenGetById() {
        String expectedName = "Event1";
        Event expectedEvent = new Event(expectedName, 10.0, EventRating.MID);

        eventService.save(expectedEvent);

        Event actualEvent = eventService.getById(expectedEvent.getId());

        assertThat(actualEvent, equalTo(expectedEvent));
    }

    @Test
    @Disabled
    void whenGetAll() {
        Event expectedEvent1 = new Event("Event1", 10.0, EventRating.MID);
        Event expectedEvent2 = new Event("Event2", 10.0, EventRating.MID);
        Event expectedEvent3 = new Event("Event3", 10.0, EventRating.MID);
        eventService.save(expectedEvent1);
        eventService.save(expectedEvent2);
        eventService.save(expectedEvent3);

        Collection<Event> actualEvents = eventService.getAll();

        //TODO why beforeEach not working
        assertThat(actualEvents.size(), equalTo(3));
    }
}
