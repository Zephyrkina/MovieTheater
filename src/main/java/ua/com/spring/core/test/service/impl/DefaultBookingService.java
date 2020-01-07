package ua.com.spring.core.test.service.impl;

import ua.com.spring.core.test.domain.Event;
import ua.com.spring.core.test.domain.EventRating;
import ua.com.spring.core.test.domain.Ticket;
import ua.com.spring.core.test.domain.User;
import ua.com.spring.core.test.exceptions.NoSuchEventException;
import ua.com.spring.core.test.service.BookingService;
import ua.com.spring.core.test.service.DiscountService;
import ua.com.spring.core.test.service.TicketService;
import ua.com.spring.core.test.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultBookingService implements BookingService {

    private TicketService ticketService;

    private UserService userService;

    private DiscountService discountService;

    public DefaultBookingService(TicketService ticketService, UserService userService, DiscountService discountService) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.discountService = discountService;
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        if (!event.getAirDates().contains(dateTime)) {
            throw new NoSuchEventException("There is no such event for specified date found");
        }

        Set<Ticket> purchasedTicketsForEvent = getPurchasedTicketsForEvent(event, dateTime);

        Set<Long> bookedSeats = purchasedTicketsForEvent.stream().map(Ticket::getSeat).collect(Collectors.toSet());

        for (Long seat : seats) {
            for(Long bookedSeat : bookedSeats ) {
                if (seat.equals(bookedSeat)) {
                    throw new NoSuchEventException("Seat #" + bookedSeat + "is already booked.");
                }
            }
        }

        int vipSeatsCount = 0;
        Set<Long> vipSeats = event.getAuditoriums().get(dateTime).getVipSeats();

        for (Long seat : seats) {

            for(Long vip : vipSeats) {
                if (seat.equals(vip)) {
                    vipSeatsCount++;
                }
            }
        }
        double totalPrice = event.getBasePrice()*vipSeatsCount*2 + event.getBasePrice()*(seats.size() - vipSeatsCount);


        EventRating rating = event.getRating();

        if (rating.equals(EventRating.HIGH)) {

            totalPrice *= 1.2;
        }

        if (rating.equals(EventRating.LOW)) {

            totalPrice *= 0.8;
        }

        double discount = (100 - discountService.getDiscount(user, event, seats.size()))/100.00;

        totalPrice *= discount;

        return totalPrice;
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
