package ua.com.spring.core.training.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.EventRating;
import ua.com.spring.core.training.domain.Ticket;
import ua.com.spring.core.training.domain.User;
import ua.com.spring.core.training.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultBookingServiceTest {

    @Mock
    private TicketService ticketService;

    @Mock
    private UserService userService;

    @Mock
    private DiscountService discountService;

    @Mock
    private AuditoriumService auditoriumService;

    @InjectMocks
    private DefaultBookingService bookingService;

    @Test
    void givenEmptyEvent_whenGetTicketsPrice_shouldThrowException() {
        Event event = null;
        LocalDateTime airDateTime = LocalDateTime.now();
        User user = new User("Fred", "Weills", "fred@gmail.com", LocalDate.now().minusYears(30).minusDays(30));
        Set<Long> seats = LongStream.range(1, 6).boxed().collect(Collectors.toSet());

        assertThrows(IllegalArgumentException.class, () -> {bookingService.getTicketsPrice(event, airDateTime, user, seats);});
    }

    @Test
    void givenEmptySeatsSet_whenGetTicketsPrice_shouldThrowException() {
        Event event = new Event("Event1", 10.0, EventRating.MID );
        LocalDateTime airDateTime = LocalDateTime.now();
        User user = new User("Fred", "Weills", "fred@gmail.com", LocalDate.now().minusYears(30).minusDays(30));
        Set<Long> seats = new HashSet<>();

        assertThrows(IllegalArgumentException.class, () -> {bookingService.getTicketsPrice(event, airDateTime, user, seats);});
    }

    //TODO test for different event ranking and discounts and vip seats

    @Test
    void givenValidEvent_andValidSeat_whenGetTicketsPrice_shouldReturnTicketsPrice() {
        Event event = new Event("Event1", 10.0, EventRating.MID );
        LocalDateTime airDateTime = LocalDateTime.now();
        User user = new User("Fred", "Weills", "fred@gmail.com", LocalDate.now().minusYears(30).minusDays(30));
        Set<Long> seats = LongStream.range(1, 6).boxed().collect(Collectors.toSet());
        double expectedTicketsPrice = 50;

        double actualTicketsPrice = bookingService.getTicketsPrice(event, airDateTime, user, seats);

        assertThat(actualTicketsPrice, equalTo(expectedTicketsPrice));
    }

    @Test
    void givenEmptyTicketSet_whenBookTickets_shouldThrowException() {
        Set<Ticket> emptyTicketSet = new HashSet<>();
        User nullUser = null;

        assertThrows(IllegalArgumentException.class, () -> { bookingService.bookTickets(emptyTicketSet, nullUser);});

        verify(ticketService, never()).save(any());
    }

    @Test
    void givenNullTicketSet_whenBookTickets_shouldThrowException() {
        Set<Ticket> emptyTicketSet = null;
        User nullUser = null;

        assertThrows(IllegalArgumentException.class, () -> { bookingService.bookTickets(emptyTicketSet, nullUser);});

        verify(ticketService, never()).save(any());
    }

    @Test
    void givenNotFulfilledTicket_whenBookTickets_shouldThrowException() {
        Set<Ticket> setWithNotFulfilledTicket = new HashSet<>();
        setWithNotFulfilledTicket.add(new Ticket());
        User nullUser = null;

        assertThrows(IllegalArgumentException.class, () -> { bookingService.bookTickets(setWithNotFulfilledTicket, nullUser);});

        verify(ticketService, never()).save(any());
    }

    @Test
    void givenValidSetOfTickets_andNullUser_whenBookTickets_shouldSaveTickets() {
        Set<Ticket> expectedTickets = createTickets(5);
        User nullUser = null;

        bookingService.bookTickets(expectedTickets, nullUser);

        verify(ticketService, atLeastOnce()).save(any());
        verify(userService, never()).save(any());
    }

    @Test
    void givenValidSetOfTickets_andValidUser_whenBookTickets_shouldSaveTickets() {
        Set<Ticket> expectedTickets = createTickets(5);
        User user = new User("Fred", "Weills", "fred@gmail.com", LocalDate.now().minusYears(30).minusDays(30));

        bookingService.bookTickets(expectedTickets, user);

        verify(ticketService, atLeastOnce()).save(any());
        verify(userService).save(any());
    }

    private Set<Ticket> createTickets(int ticketsNumber) {
        User user = null;
        Event event = new Event("Event1", 10.0, EventRating.MID );
        LocalDateTime airDateTime = LocalDateTime.now();

        Set<Ticket> tickets = new HashSet<>();
        for(int i = 0; i < ticketsNumber; i++) {
            Ticket ticket = new Ticket(user, event, airDateTime, (long) i+10);
            tickets.add(ticket);
        }
        return tickets;
    }

}