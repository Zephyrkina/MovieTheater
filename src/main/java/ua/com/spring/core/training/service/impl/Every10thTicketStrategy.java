package ua.com.spring.core.training.service.impl;

import ua.com.spring.core.training.domain.User;
import ua.com.spring.core.training.service.DiscountStrategy;

import static ua.com.spring.core.training.domain.DiscountRating.EVERY_10TH_TICKET_DISCOUNT;
import static ua.com.spring.core.training.domain.DiscountRating.NO_DISCOUNT;

public class Every10thTicketStrategy implements DiscountStrategy {

    @Override
    public Byte execute(User user, long numberOfTickets) {
        if (user == null && numberOfTickets >=10) {
            return EVERY_10TH_TICKET_DISCOUNT.getDiscountValue();
        }

        if (user != null) {
            long boughtTickets = user.getTickets().size();
            long totalTickets = boughtTickets + numberOfTickets;

            if (totalTickets % 10 == 0) {
                return EVERY_10TH_TICKET_DISCOUNT.getDiscountValue();
            }
        }

        return NO_DISCOUNT.getDiscountValue();
    }
}
