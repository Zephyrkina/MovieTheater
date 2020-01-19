package ua.com.spring.core.training.dao;

import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.Ticket;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface TicketRepository extends AbstractRepository<Ticket> {
    Set<Ticket> getByEventAndTime(Event event, LocalDateTime time);
}
