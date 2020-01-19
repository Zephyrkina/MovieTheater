package ua.com.spring.core.training.dao.impl;

import lombok.extern.slf4j.Slf4j;
import ua.com.spring.core.training.dao.EventRepository;
import ua.com.spring.core.training.domain.Event;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class EventRepositoryImpl implements EventRepository {

    private static AtomicLong eventIdCount = new AtomicLong(0);

    private static Map<Long, Event> events = new HashMap<>();

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
        if (object.getId() == null) {
            object.setId(eventIdCount.getAndIncrement());
        }
        return events.put(object.getId(), object);
    }

    @Override
    public Event remove(Event object) {
        return events.remove(object.getId());
    }

    @Override
    public Optional<Event> getByName(String name) {
        return events.values().stream().filter(event -> event.getName().equals(name)).findFirst();
    }
}
