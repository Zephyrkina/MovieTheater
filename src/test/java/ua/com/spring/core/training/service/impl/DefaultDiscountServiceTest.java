package ua.com.spring.core.training.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.EventRating;
import ua.com.spring.core.training.domain.User;
import ua.com.spring.core.training.service.DiscountStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultDiscountServiceTest {

    @Spy
    private List<DiscountStrategy> discountStrategies = new ArrayList<>(2);

    @InjectMocks
    private DefaultDiscountService discountService;

    private User user;

    private Event event;

    private DiscountStrategy birthday;

    private DiscountStrategy every10thTicket;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .firstName("John")
                .lastName("Smith")
                .email("johnsm@gmail.com")
                .birthday(LocalDate.now().minusYears(30).minusDays(3))
                .build();

        event = new Event("mon", 10.0, EventRating.MID );

        birthday = mock(BirthdayStrategy.class);
        every10thTicket = mock(Every10thTicketStrategy.class);

        discountStrategies.add(birthday);
        discountStrategies.add(every10thTicket);
    }

    @Test
    void givenFivePercent_andFiftyPercentDiscount_whenGetDiscount_thenShouldReturnBiggestNumber(){
        long numberOfTickets = 10l;
        byte expectedDiscount = 50;

        when(birthday.execute(user, numberOfTickets)).thenReturn((byte) 5);
        when(every10thTicket.execute(user, numberOfTickets)).thenReturn((byte) 50);


        byte actualDiscount = discountService.getDiscount(user, event, numberOfTickets);

        assertThat(actualDiscount, equalTo(expectedDiscount));
    }

    @Test
    void givenNoDiscount_whenGetDiscount_thenShouldReturn0Discount(){
        long numberOfTickets = 10l;
        byte expectedDiscount = 0;

        when(birthday.execute(user, numberOfTickets)).thenReturn((byte) 0);
        when(every10thTicket.execute(user, numberOfTickets)).thenReturn((byte) 0);

        byte actualDiscount = discountService.getDiscount(user, event, numberOfTickets);

        assertThat(actualDiscount, equalTo(expectedDiscount));
    }

}