package ua.com.spring.core.training.service;

import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.Ticket;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Set;

public interface TicketService extends AbstractDomainObjectService<Ticket> {

    @Nonnull Set<Ticket> getByEventAndTime(@Nonnull Event event, @Nonnull LocalDateTime dateTime);

}
