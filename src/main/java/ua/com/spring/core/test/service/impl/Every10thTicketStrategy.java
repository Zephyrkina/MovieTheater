package ua.com.spring.core.test.service.impl;

import ua.com.spring.core.test.domain.User;
import ua.com.spring.core.test.service.DiscountStrategy;

public class Every10thTicketStrategy implements DiscountStrategy {

    @Override
    public Byte execute(User user, long numberOfTickets) {
        if (user == null && numberOfTickets >=10) {
            return 50;
        }

        if (user != null) {
            long boughtTickets = user.getTickets().size();
            long totalTickets = boughtTickets + numberOfTickets;
            if (totalTickets % 10 == 0) {
                return 50;
            }

        }

        return 0;
    }
}
