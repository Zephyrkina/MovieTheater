package ua.com.spring.core.training.service.impl;

import ua.com.spring.core.training.dao.TicketRepository;
import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.Ticket;
import ua.com.spring.core.training.service.TicketService;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

public class DefaultTicketService implements TicketService {

    private TicketRepository ticketRepository;

    public DefaultTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket save(@Nonnull Ticket object) {
        return ticketRepository.save(object);
    }

    @Override
    public void remove(@Nonnull Ticket object) {
        ticketRepository.remove(object);
    }

    @Override
    public Ticket getById(@Nonnull Long id) {
        return ticketRepository.getById(id).orElseThrow(() -> new RuntimeException("No ticket with such id found"));
    }

    @Nonnull
    @Override
    public Collection<Ticket> getAll() {
        return ticketRepository.getAll();
    }

    public Set<Ticket> getByEventAndTime(Event event, LocalDateTime dateTime) {
        return ticketRepository.getByEventAndTime(event, dateTime);
    }
}
