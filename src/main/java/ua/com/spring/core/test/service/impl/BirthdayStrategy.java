package ua.com.spring.core.test.service.impl;

import ua.com.spring.core.test.domain.User;
import ua.com.spring.core.test.service.DiscountStrategy;

public class BirthdayStrategy implements DiscountStrategy {

    @Override
    public byte execute(User user, long numberOfTickets) {
        return 0;
    }
}
