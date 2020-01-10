package ua.com.spring.core.training.service.impl;

import ua.com.spring.core.training.dao.EventRepository;
import ua.com.spring.core.training.domain.Auditorium;
import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.exceptions.EventNotFoundException;
import ua.com.spring.core.training.service.EventService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public class DefaultEventService implements EventService {

    private EventRepository eventRepository;

    public DefaultEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return eventRepository.getByName(name).orElseThrow(() -> new EventNotFoundException("No event with such name found"));
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
        return eventRepository.getById(id).orElseThrow(() -> new EventNotFoundException("No event with such id found"));
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return eventRepository.getAll();
    }

    public boolean addAirDateTime(Event event, LocalDateTime dateTime, Auditorium auditorium) {
        boolean result = event.getAirDates().add(dateTime);
        if (result) {
            event.getAuditoriums().put(dateTime, auditorium);
        }
        return result;
    }

    public boolean removeAirDateTime(Event event, LocalDateTime dateTime) {
        boolean result = event.getAirDates().remove(dateTime);
        if (result) {
            event.getAuditoriums().remove(dateTime);
        }
        return result;
    }

    private boolean addAirDateTime(Event event, LocalDateTime dateTime) {
        return event.getAirDates().add(dateTime);
    }

    private boolean assignAuditorium(Event event, LocalDateTime dateTime, Auditorium auditorium) {
        if (event.getAirDates().contains(dateTime)) {
            event.getAuditoriums().put(dateTime, auditorium);
            return true;
        } else {
            return false;
        }
    }

    private boolean removeAuditoriumAssignment(Event event, LocalDateTime dateTime) {
        return event.getAuditoriums().remove(dateTime) != null;
    }


    private boolean airsOnDateTime(Event event, LocalDateTime dateTime) {
        return event.getAirDates().stream().anyMatch(dt -> dt.equals(dateTime));
    }

    private boolean airsOnDate(Event event, LocalDate date) {
        return event.getAirDates().stream().anyMatch(dt -> dt.toLocalDate().equals(date));
    }

    private boolean airsOnDates(Event event, LocalDate from, LocalDate to) {
        return event.getAirDates().stream()
                .anyMatch(dt -> dt.toLocalDate().compareTo(from) >= 0 && dt.toLocalDate().compareTo(to) <= 0);
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
