package ua.com.spring.core.training.service.impl;

import ua.com.spring.core.training.domain.User;
import ua.com.spring.core.training.service.DiscountStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static ua.com.spring.core.training.domain.DiscountRating.BIRTHDAY_DISCOUNT;
import static ua.com.spring.core.training.domain.DiscountRating.NO_DISCOUNT;

public class BirthdayStrategy implements DiscountStrategy {
    private static final Integer FIVE_DAYS_DISCOUNT_INTERVAL = 5;

    @Override
    public Byte execute(User user, long numberOfTickets) {
        if (user == null) {
            return NO_DISCOUNT.getDiscountValue();
        }

        LocalDate today = LocalDate.now();
        LocalDate thisYearBirthdayDate = defineThisYearBirthdayDate(user);

        if (today.minusDays(FIVE_DAYS_DISCOUNT_INTERVAL).isBefore(thisYearBirthdayDate)
                && today.plusDays(FIVE_DAYS_DISCOUNT_INTERVAL).isAfter(thisYearBirthdayDate) ) {
            return BIRTHDAY_DISCOUNT.getDiscountValue();
        }

        return NO_DISCOUNT.getDiscountValue();
    }

    private LocalDate defineThisYearBirthdayDate(User user) {
        int day = user.getBirthday().getDayOfMonth();
        Month month = user.getBirthday().getMonth();
        int year = LocalDateTime.now().getYear();

        return LocalDate.of(year, month, day);
    }
}
