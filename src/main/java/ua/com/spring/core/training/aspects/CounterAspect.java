package ua.com.spring.core.training.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.Ticket;
import ua.com.spring.core.training.service.EventCounterService;

import java.util.Set;

@Slf4j
@Aspect
public class CounterAspect {

    @Autowired
    private EventCounterService eventCounterService;

    @AfterReturning(pointcut = "execution(* ua.com.spring.core.training.service.impl.DefaultEventService.getByName(..))",
            returning = "event")
    public void countTimesEventGetByName(Event event) {
        eventCounterService.increaseGetEventByNameCounter(event);
        log.info("Registered name request for event: id: {}, name: {}", event.getId(), event.getName());
    }

    @After("execution(* ua.com.spring.core.training.service.impl.DefaultBookingService.getTicketsPrice(..))")
    public void countTimesGetEventTicketPrice(JoinPoint joinPoint) {
        Object[] joinPointArgs = joinPoint.getArgs();
        for (Object o : joinPointArgs) {
            if (o instanceof Event) {
                Event requestedEvent = (Event) o;
                eventCounterService.increaseGetTicketPriceForEventCounter(requestedEvent);
                log.info("Registered price request for event: id: {}, name: {}", requestedEvent.getId(), requestedEvent.getName());

            }
        }
    }

    @After("execution(* ua.com.spring.core.training.service.impl.DefaultBookingService.bookTickets(..))")
    public void countTimesBookEventTicket(JoinPoint joinPoint) {
        Object[] joinPointArgs = joinPoint.getArgs();
        for (Object o : joinPointArgs) {
            if (o instanceof Set) {
                Set<Ticket> ticketSet = (Set<Ticket>) o;
                Event event = ticketSet.iterator().next().getEvent();
                eventCounterService.increaseBookTicketsForEventCounter(event);
                log.info("Registered booking request for event: id: {}, name: {}", event.getId(), event.getName());

            }
        }
    }
}
