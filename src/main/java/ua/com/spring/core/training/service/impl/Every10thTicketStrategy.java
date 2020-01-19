package ua.com.spring.core.training.service.impl;

import ua.com.spring.core.training.domain.User;
import ua.com.spring.core.training.service.DiscountStrategy;

import static ua.com.spring.core.training.domain.Discount.EVERY_10TH_TICKET_DISCOUNT;
import static ua.com.spring.core.training.domain.Discount.NO_DISCOUNT;

public class Every10thTicketStrategy implements DiscountStrategy {

    @Override
    public Byte execute(User user, long numberOfTickets) {
        if (numberOfTickets >=10) {
            return EVERY_10TH_TICKET_DISCOUNT.getValue();
        }

        if (user != null && user.getTickets() != null) {
            long boughtTickets = user.getTickets().size();
            long totalTickets = boughtTickets + numberOfTickets;

            if ( (boughtTickets % 10) + numberOfTickets >= 10) {
                return EVERY_10TH_TICKET_DISCOUNT.getValue();
            }
        }

        return NO_DISCOUNT.getValue();
    }
}
