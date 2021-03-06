package ua.com.spring.core.training.dao.impl;

import lombok.extern.slf4j.Slf4j;
import ua.com.spring.core.training.dao.TicketRepository;
import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.Ticket;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
public class TicketRepositoryImpl implements TicketRepository {

    private static AtomicLong ticketIdCount = new AtomicLong(0);

    private static Map<Long, Ticket> tickets = new HashMap<>();

    @Override
    public List<Ticket> getAll() {
        return new ArrayList<>(tickets.values());
    }

    @Override
    public Optional<Ticket> getById(Long id) {
        return Optional.of(tickets.get(id));
    }

    @Override
    public Ticket save(Ticket object) {
        if (object.getId() == null) {
            object.setId(ticketIdCount.getAndIncrement());
        }
        return tickets.put(object.getId(), object);
    }

    @Override
    public Ticket remove(Ticket object) {
        return tickets.remove(object.getId());
    }

    @Override
    public Set<Ticket> getByEventAndTime(Event event, LocalDateTime time) {
        return tickets.values().stream().filter(ticket -> ticket.getEvent().equals(event))
                .filter(ticket -> ticket.getEvent().getAirDates().contains(time))
                .collect(Collectors.toSet());
    }
}
