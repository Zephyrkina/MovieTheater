package ua.com.spring.core.training.service.impl;

import ua.com.spring.core.training.domain.*;
import ua.com.spring.core.training.service.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultBookingService implements BookingService {

    private TicketService ticketService;

    private UserService userService;

    private DiscountService discountService;

    private AuditoriumService auditoriumService;

    public DefaultBookingService(TicketService ticketService, UserService userService, DiscountService discountService, AuditoriumService auditoriumService) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.discountService = discountService;
        this.auditoriumService = auditoriumService;
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return ticketService.getByEventAndTime(event, dateTime);
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        checkSeatsAreNotBooked(event, dateTime, seats);

        long vipSeatsCount = auditoriumService.countVipSeats(seats, event.getAuditoriums().get(dateTime));
        long usualSeatsCount = seats.size() - vipSeatsCount;

        double usualSeatsPrice = calculateUsualSeatsPrice(event, usualSeatsCount);
        double vipSeatsPrice = calculateVipSeatsPrice(event, vipSeatsCount);
        double eventRating = getEventRating(event);
        double discount = getDiscount(event, user, seats);

        return (usualSeatsPrice + vipSeatsPrice) * eventRating + discount;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        checkTicketsInfoIsFulfilled(tickets);
        checkTicketsAreNotBooked(tickets);

        saveTickets(tickets);

        User user = userService.getById(tickets.iterator().next().getUser().getId());

        if (user != null) {
            updateUserTickets(user, tickets);
        }
    }

    private void checkTicketsInfoIsFulfilled(@Nonnull Set<Ticket> tickets) {
        for(Ticket ticket : tickets) {
            if (ticket.getEvent() == null || ticket.getSeat() == null || ticket.getDateTime() == null) {
                throw new IllegalArgumentException("Ticket info should be fulfilled: " + ticket.toString());
            }
        }
    }

    private void checkTicketsAreNotBooked(@Nonnull Set<Ticket> tickets) {
        Ticket exampleTicket = tickets.stream().findFirst().get();
        Set<Ticket> bookedTickets = getPurchasedTicketsForEvent(exampleTicket.getEvent(), exampleTicket.getDateTime());

         tickets.stream().filter(ticket -> bookedTickets.contains(ticket)).findAny()
                 .ifPresent(ticket -> { throw new IllegalArgumentException("Ticket is already booked: " + ticket.toString());});
    }

    private void saveTickets(Set<Ticket> tempTickets) {
        tempTickets.forEach(t -> ticketService.save(t));
    }

    private void updateUserTickets(User user, Set<Ticket> tempTickets) {
        user.getTickets().addAll(tempTickets);
        userService.save(user);
    }

    private void checkSeatsAreNotBooked(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nonnull Set<Long> seats) {
        Set<Long> bookedSeats = getPurchasedTicketsForEvent(event, dateTime).stream().map(Ticket::getSeat).collect(Collectors.toSet());

        seats.stream().filter(seat -> (bookedSeats.contains(seat))).findAny()
                .ifPresent(seat -> { throw new IllegalArgumentException("Seat #" + seat + "is already booked."); } );
    }

    private double getEventRating(Event event) {
        if (event.getRating().equals(EventRating.LOW)){
            return 0.8;
        } else if (event.getRating().equals(EventRating.HIGH)) {
            return 1.2;
        }
        return 1.0;
    }

    private double getDiscount(@Nonnull Event event, @Nullable User user, @Nonnull Set<Long> seats) {
        return (100 - discountService.getDiscount(user, event, seats.size()))/100.00;
    }

    private double calculateVipSeatsPrice(@Nonnull Event event, long vipSeatsCount) {
        return event.getBasePrice()*vipSeatsCount*2;
    }

    private double calculateUsualSeatsPrice(@Nonnull Event event, long usualSeatsCount) {
        return event.getBasePrice()*usualSeatsCount;
    }
}
