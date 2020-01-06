package ua.com.spring.core.test.dao;

import ua.com.spring.core.test.domain.Event;

import java.time.LocalDateTime;
import java.util.*;

public class EventRepository implements AbstractRepository<Event> {
    public static Map<Long, Event> events;

    static {
        events = new HashMap<>();
        events.put(1L, new Event());
        events.put(2L, new Event());
        events.put(3L, new Event());
    }

    @Override
    public List<Event> getAll() {
        return new ArrayList<>(events.values());
    }

    @Override
    public Optional<Event> getById(Long id) {
        return Optional.of(events.get(id));
    }

    @Override
    public Event save(Event object) {
        return events.put(object.getId(), object);
    }

    @Override
    public Event remove(Event object) {
        return events.remove(object);
    }

    @Override
    public Event update(Event object) {
        events.remove(object);
        return events.put(object.getId(), object);
    }

    public Optional<Event> getByName(String name) {
        return events.values().stream().filter(event -> event.getName().equals(name)).findFirst();
    }

}
