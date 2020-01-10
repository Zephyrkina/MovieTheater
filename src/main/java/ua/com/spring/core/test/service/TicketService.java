package ua.com.spring.core.test.service;

import ua.com.spring.core.test.domain.Event;
import ua.com.spring.core.test.domain.Ticket;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Set;

public interface TicketService extends AbstractDomainObjectService<Ticket> {

    @Nonnull Set<Ticket> getByEventAndTime(@Nonnull Event event, @Nonnull LocalDateTime dateTime);

}
