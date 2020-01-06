package ua.com.spring.core.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.spring.core.test.dao.EventRepository;
import ua.com.spring.core.test.domain.Event;
import ua.com.spring.core.test.service.EventService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class DefaultEventService implements EventService {

    private EventRepository eventRepository;

    @Autowired
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
