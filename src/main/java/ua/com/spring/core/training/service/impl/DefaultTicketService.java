package ua.com.spring.core.training.service.impl;

import ua.com.spring.core.training.dao.TicketRepository;
import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.Ticket;
import ua.com.spring.core.training.exceptions.TicketNotFoundException;
import ua.com.spring.core.training.service.TicketService;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public class DefaultTicketService implements TicketService {

    private TicketRepository ticketRepository;

    public DefaultTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket save(@Nonnull Ticket object) {
        checkArgument(object != null, "Ticket shouldn't be null");
        return ticketRepository.save(object);
    }

    @Override
    public void remove(@Nonnull Ticket object) {
        checkArgument(object != null, "Ticket shouldn't be null");
        ticketRepository.remove(object);
    }

    @Override
    public Ticket getById(@Nonnull Long id) {
        checkArgument(id != null, "Ticket id shouldn't be null");
        return ticketRepository.getById(id).orElseThrow(() -> new TicketNotFoundException("No ticket with such id found"));
    }

    @Nonnull
    @Override
    public Collection<Ticket> getAll() {
        return ticketRepository.getAll();
    }

    public Set<Ticket> getByEventAndTime(Event event, LocalDateTime dateTime) {
        checkArgument(event != null, "Event shouldn't be null");
        return ticketRepository.getByEventAndTime(event, dateTime);
    }
}
