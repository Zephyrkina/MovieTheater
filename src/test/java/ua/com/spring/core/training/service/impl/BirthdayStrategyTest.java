package ua.com.spring.core.training.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.spring.core.training.domain.User;
import ua.com.spring.core.training.service.DiscountStrategy;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class BirthdayStrategyTest {

    private DiscountStrategy birthdayStrategy;

    @BeforeEach
    void setUp(){
        birthdayStrategy = new BirthdayStrategy();
    }

    @Test
    void whenUserHasBirthdayWithin5Days_thenReturn5Discount() {
        User user = User.builder()
                .firstName("John")
                .lastName("Smith")
                .email("johnsm@gmail.com")
                .birthday(LocalDate.now().minusYears(30).minusDays(3))
                .build();

        byte expectedBirthdayDiscount = 5;

        long numberOfTickets = 5L;

        byte actualDiscount = birthdayStrategy.execute(user, numberOfTickets);

        assertThat(actualDiscount, is(equalTo(expectedBirthdayDiscount)));
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

        byte actualDiscount = birthdayStrategy.execute(user, numberOfTickets);

        assertThat(actualDiscount, is(equalTo(expectedZeroDiscount)));
    }

    @Test
    void whenUserIsNull_thenReturn0Discount() {
        User user = null;
        byte expectedZeroDiscount = 0;
        long numberOfTickets = 5L;

        byte actualDiscount = birthdayStrategy.execute(user, numberOfTickets);

        assertThat(actualDiscount, is(equalTo(expectedZeroDiscount)));
    }
}