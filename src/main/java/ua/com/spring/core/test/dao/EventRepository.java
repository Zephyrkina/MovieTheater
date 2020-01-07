package ua.com.spring.core.test.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ua.com.spring.core.test.domain.Event;

import java.util.*;

@Slf4j
@Repository
public class EventRepository implements AbstractRepository<Event> {

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
        //TODO remake ids
        log.info("Save event: {}", object);
        object.setId(new Random().nextLong());
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
