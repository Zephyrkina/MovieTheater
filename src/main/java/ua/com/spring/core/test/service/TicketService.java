package ua.com.spring.core.test.service;

import ua.com.spring.core.test.domain.Event;
import ua.com.spring.core.test.domain.Ticket;

import java.time.LocalDateTime;
import java.util.Set;

public interface TicketService extends AbstractDomainObjectService<Ticket> {
    Set<Ticket> getByEventAndTime(Event event, LocalDateTime dateTime);

}
