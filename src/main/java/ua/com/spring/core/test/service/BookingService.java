package ua.com.spring.core.test.service;

import ua.com.spring.core.test.domain.Event;
import ua.com.spring.core.test.domain.Ticket;
import ua.com.spring.core.test.domain.User;

import java.time.LocalDateTime;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface BookingService {

    double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user,
                                  @Nonnull Set<Long> seats);

    void bookTickets(@Nonnull Set<Ticket> tickets);

    @Nonnull Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime);

}
