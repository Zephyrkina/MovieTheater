package ua.com.spring.core.test.service.impl;

import ua.com.spring.core.test.dao.EventRepository;
import ua.com.spring.core.test.domain.Event;
import ua.com.spring.core.test.service.EventService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

public class DefaultEventService implements EventService {

    private EventRepository eventRepository;


    public DefaultEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return eventRepository.getByName(name).orElseThrow(() -> new RuntimeException("No event with such name found"));
    }

    @Override
    public Event save(@Nonnull Event object) {
        return eventRepository.save(object);

    }

    @Override
    public void remove(@Nonnull Event object) {
        eventRepository.remove(object);
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return eventRepository.getById(id).orElseThrow(() -> new RuntimeException("No event with such id found"));
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return eventRepository.getAll();
    }

    @Override
    public Event update(Event object) {
        return eventRepository.update(object);
    }

 /*
    (OPTIONAL)
   public List<Event> getNextEvents(LocalDateTime to){
        return null;
    }

    (OPTIONAL)
     public List<Event> getForDateRange(LocalDateTime from, LocalDateTime to){
        return eventRepository.getForDateRange(from, to);



    */
}
