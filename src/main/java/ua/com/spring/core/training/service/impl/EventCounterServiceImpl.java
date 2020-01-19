package ua.com.spring.core.training.service.impl;

import ua.com.spring.core.training.dao.EventCounterRepository;
import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.EventCounter;
import ua.com.spring.core.training.service.EventCounterService;

public class EventCounterServiceImpl implements EventCounterService {

    private EventCounterRepository eventCounterRepository;

    public EventCounterServiceImpl(EventCounterRepository eventCounterRepository) {
        this.eventCounterRepository = eventCounterRepository;
    }

    @Override
    public Long increaseGetEventByNameCounter(Event event) {
        EventCounter eventCounter = eventCounterRepository.getById(event.getId()).orElse(createEventCounter(event));

        long newNameCountValue = eventCounter.getNameRequests() + 1L;
        eventCounter.setNameRequests(newNameCountValue);

        eventCounterRepository.save(eventCounter);

        return newNameCountValue;
    }

    @Override
    public Long increaseGetTicketPriceForEventCounter(Event event) {
        EventCounter eventCounter = eventCounterRepository.getById(event.getId()).orElse(createEventCounter(event));

        long newPriceCountValue = eventCounter.getPriceRequests() + 1L;
        eventCounter.setPriceRequests(newPriceCountValue);

        eventCounterRepository.save(eventCounter);

        return newPriceCountValue;
    }

    @Override
    public Long increaseBookTicketsForEventCounter(Event event) {
        EventCounter eventCounter = eventCounterRepository.getById(event.getId()).orElse(createEventCounter(event));

        long newBookCountValue = eventCounter.getBookRequests() + 1L;
        eventCounter.setBookRequests(newBookCountValue);

        eventCounterRepository.save(eventCounter);

        return newBookCountValue;
    }

    private EventCounter createEventCounter(Event event) {
        return EventCounter.builder()
                .eventId(event.getId())
                .build();
    }
}
