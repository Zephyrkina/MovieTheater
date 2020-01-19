package ua.com.spring.core.training.dao.impl;
import ua.com.spring.core.training.dao.EventCounterRepository;
import ua.com.spring.core.training.domain.EventCounter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EventCounterRepositoryImpl implements EventCounterRepository {

    private static Map<Long, EventCounter> eventCounters = new HashMap<>();

   /* @Override
    public void addNameCountForEvent(Event event) {
        EventCounter eventCounter = eventCounters.get(event.getId());
        if (eventCounter == null) {
            eventCounter = new EventCounter();
        }
        eventCounter.setNameRequests(eventCounter.getNameRequests() + 1 );
        eventCounters.put(event.getId(), eventCounter);
    }

    @Override
    public void addPriceCountForEvent(Event event) {
        EventCounter eventCounter = eventCounters.get(event.getId());
        if (eventCounter == null) {
            eventCounter = new EventCounter();
        }
        eventCounter.setPriceRequests(eventCounter.getPriceRequests() + 1 );
        eventCounters.put(event.getId(), eventCounter);
    }

    @Override
    public void addBookCountForEvent(Event event) {
        EventCounter eventCounter = eventCounters.get(event.getId());
        if (eventCounter == null) {
            eventCounter = new EventCounter();
        }
        eventCounter.setBookRequests(eventCounter.getBookRequests() + 1 );
        eventCounters.put(event.getId(), eventCounter);
    }*/

    @Override
    public Optional<EventCounter> getById(Long id) {
        return Optional.ofNullable(eventCounters.get(id));
    }

    @Override
    public EventCounter save(EventCounter object) {
        return eventCounters.put(object.getEventId(), object);
    }
}
