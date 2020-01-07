package ua.com.spring.core.test.service;

import ua.com.spring.core.test.domain.User;

public interface DiscountStrategy {

    Byte execute(User user, long numberOfTickets);

}
