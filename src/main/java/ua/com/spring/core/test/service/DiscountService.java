package ua.com.spring.core.test.service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.com.spring.core.test.domain.Event;
import ua.com.spring.core.test.domain.User;

public interface DiscountService {

    /**
     * Getting discount based on some rules for user that buys some number of
     * tickets for the specific date time of the event
     * 
     * @param user
     *            User that buys tickets. Can be <code>null</code>
     * @param event
     *            Event that tickets are bought for
     * @param numberOfTickets
     *            Number of tickets that user buys
     * @return discount value from 0 to 100
     */
    Byte getDiscount(@Nullable User user, @Nonnull Event event, long numberOfTickets);

}
