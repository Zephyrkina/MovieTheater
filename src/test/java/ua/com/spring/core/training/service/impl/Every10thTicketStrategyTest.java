package ua.com.spring.core.training.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.EventRating;
import ua.com.spring.core.training.domain.Ticket;
import ua.com.spring.core.training.domain.User;
import ua.com.spring.core.training.service.DiscountStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NavigableSet;
import java.util.TreeSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:application-test-context.xml"})
class Every10thTicketStrategyTest {

    @Autowired
    private DiscountStrategy every10thTicketStrategy;

    @Test
    void whenUserBuyNext10thTicket_thenReturn50Discount() {
        User user = User.builder()
                .firstName("John")
                .lastName("Smith")
                .email("johnsm@gmail.com")
                .birthday(LocalDate.now().minusYears(30).minusDays(3))
                .build();

        Event event = new Event("mon", 10.0, EventRating.MID);
        LocalDateTime airDateTime = LocalDateTime.now();
        NavigableSet<Ticket> tickets = createUserTickets(user, event, airDateTime, 3);
        user.setTickets(tickets);

        long numberOfTickets = 8L;
        byte expectedEvery10thTicketDiscount = 50;

        byte actualDiscount = every10thTicketStrategy.execute(user, numberOfTickets);

        assertThat(actualDiscount, is(equalTo(expectedEvery10thTicketDiscount)));
    }

    @Test
    void givenEmptyUser_andFiveTickets_whenExecuteStrategy_thenReturn0Discount() {
        User user = null;

        long numberOfTickets = 5L;
        byte expectedEvery10thTicketDiscount = 50;

        byte actualDiscount = every10thTicketStrategy.execute(user, numberOfTickets);

        assertThat(actualDiscount, is(equalTo(expectedEvery10thTicketDiscount)));
    }

    @Test
    void givenEmptyUser_andTenTickets_whenExecuteStrategy_thenReturn0Discount() {
        User user = null;

        long numberOfTickets = 11L;
        byte expectedZeroDiscount = 0;

        byte actualDiscount = every10thTicketStrategy.execute(user, numberOfTickets);

        assertThat(actualDiscount, is(equalTo(expectedZeroDiscount)));
    }

    @Test
    void givenEmptyUser_andElevenTickets_whenExecuteStrategy_thenReturn50Discount() {
        User user = null;

        long numberOfTickets = 5L;
        byte expectedZeroDiscount = 0;

        byte actualDiscount = every10thTicketStrategy.execute(user, numberOfTickets);

        assertThat(actualDiscount, is(equalTo(expectedZeroDiscount)));
    }


    @Test
    void whenUserDoesNotHaveBirthdayWithin5Days_thenReturn0Discount() {
        User user = User.builder()
                .firstName("John")
                .lastName("Smith")
                .email("johnsm@gmail.com")
                .birthday(LocalDate.now().minusYears(30).minusDays(30))
                .build();

        byte expectedZeroDiscount = 0;

        long numberOfTickets = 5L;

        byte actualDiscount = every10thTicketStrategy.execute(user, numberOfTickets);

        assertThat(actualDiscount, is(equalTo(expectedZeroDiscount)));
    }

    @Test
    void whenUserIsNull_thenReturn0Discount() {
        User user = null;
        byte expectedZeroDiscount = 0;
        long numberOfTickets = 5L;

        byte actualDiscount = every10thTicketStrategy.execute(user, numberOfTickets);

        assertThat(actualDiscount, is(equalTo(expectedZeroDiscount)));
    }

    private NavigableSet<Ticket> createUserTickets(User user, Event event, LocalDateTime airDateTime, long ticketsQuantity) {
        NavigableSet<Ticket> tickets = new TreeSet<>();

        for (int i = 0; i < ticketsQuantity; i++) {
            Ticket ticket = new Ticket(user, event, airDateTime, Long.valueOf(i));
            tickets.add(ticket);
        }

        return tickets;
    }
}