package ua.com.spring.core.training.service;

import ua.com.spring.core.training.domain.Event;

public interface EventCounterService {

    Long increaseGetEventByNameCounter(Event event);

    Long increaseGetTicketPriceForEventCounter(Event event);

    Long increaseBookTicketsForEventCounter(Event event);
}
