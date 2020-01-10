package ua.com.spring.core.training.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ua.com.spring.core.training.domain.Event;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class EventRepository implements AbstractRepository<Event> {

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
        return events.remove(object);
    }


    public Optional<Event> getByName(String name) {
        return events.values().stream().filter(event -> event.getName().equals(name)).findFirst();
    }

}
