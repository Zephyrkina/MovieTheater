package ua.com.spring.core.test.service.impl;

import ua.com.spring.core.test.domain.Event;
import ua.com.spring.core.test.domain.Ticket;
import ua.com.spring.core.test.domain.User;
import ua.com.spring.core.test.exceptions.NoSuchEventException;
import ua.com.spring.core.test.service.BookingService;
import ua.com.spring.core.test.service.TicketService;
import ua.com.spring.core.test.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultBookingService implements BookingService {

    private TicketService ticketService;

    private UserService userService;

    public DefaultBookingService(TicketService ticketService, UserService userService) {
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        // проверка есть ли у юзера скидки, проверка рейтинга ивента, проверка на вип

        if (!event.getAirDates().contains(dateTime)) {
            throw new NoSuchEventException("There is no such event for specified date found");
        }

        Set<Ticket> purchasedTicketsForEvent = getPurchasedTicketsForEvent(event, dateTime);

        Set<Long> bookedSeats = purchasedTicketsForEvent.stream().map(t -> t.getSeat()).collect(Collectors.toSet());

        for (Long seat : seats) {
            for(Long bookedSeat : bookedSeats ) {
                if (seat.equals(bookedSeat)) {
                    throw new NoSuchEventException("Seat #" + bookedSeat + "is already booked.");
                }
            }
        }
        return event.getBasePrice()*seats.size();
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        // проверить, что инфа о тикетах заполнена

        List<Ticket> tempTickets = tickets.stream().map(ticket -> {
            ticket.setBooked(true);
            return ticket;
        }).collect(Collectors.toList());

        tempTickets.stream().forEach(t -> ticketService.update((Ticket) t));

        try {
            User tempUser = userService.getById(tempTickets.get(0).getId());

            tempUser.getTickets().addAll(tempTickets);

            userService.update(tempUser);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return ticketService.getByEventAndTime(event, dateTime);
    }
}
