package ua.com.spring.core.test.service.impl;

import ua.com.spring.core.test.domain.User;
import ua.com.spring.core.test.service.DiscountStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class BirthdayStrategy implements DiscountStrategy {

    @Override
    public Byte execute(User user, long numberOfTickets) {
        int day = user.getBirthday().getDayOfMonth();
        Month month = user.getBirthday().getMonth();
        int year = LocalDateTime.now().getYear();
        LocalDate thisYearBirthdayDate = LocalDate.of(year, month, day);

        if (LocalDate.now().minusDays(5).isBefore(thisYearBirthdayDate) && LocalDate.now().plusDays(5).isAfter(thisYearBirthdayDate) ) {
            return 5;
        }

        return 0;
    }
}
