package ua.com.spring.core.training.service;

import ua.com.spring.core.training.domain.User;

public interface DiscountStrategy {

    Byte execute(User user, long numberOfTickets);

}
