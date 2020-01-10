package ua.com.spring.core.training;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.com.spring.core.training.domain.*;
import ua.com.spring.core.training.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Slf4j
public class App 
{
    public static void main( String[] args )
    {

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("app-config.xml");
        ctx.refresh();

        AuditoriumService auditoriumService = (AuditoriumService) ctx.getBean("auditoriumService");
        BookingService bookingService = (BookingService) ctx.getBean("bookingService");
        EventService eventService = (EventService) ctx.getBean("eventService");
        UserService userService = (UserService) ctx.getBean("userService");
        TicketService ticketService = (TicketService) ctx.getBean("ticketService");


        //Set<Auditorium> auditoriums = auditoriumService.getAll();
        Auditorium redAuditorium = auditoriumService.getByName("Red");
        LocalDateTime airDateTime = LocalDateTime.now();
        Set<Long> seats = LongStream.range(1, 6).boxed().collect(Collectors.toSet());



        //for Admins
        //save event

        Event event = new Event("mon", 10.0, EventRating.MID );
        eventService.addAirDateTime(event, airDateTime , redAuditorium);

        Event savedEvent = eventService.save(event);

        //get events

        List<Event> events = (List<Event>) eventService.getAll();
        //events.stream().forEach(System.out::println);

        //view purchased tickets

        Set<Ticket> purchasedTicketsForEvent = bookingService.getPurchasedTicketsForEvent(event, airDateTime);
        purchasedTicketsForEvent.stream().forEach(System.out::print);


        //for Users
        //register

        User user = new User("Fred", "Weills", "fred@jamb.com", LocalDate.now().minusYears(30).minusDays(30));
        userService.save(user);
        User savedUser = userService.getUserByEmail("fred@jamb.com");
        log.info("Saved user {} ", savedUser);

        //view events with air dates and times

        List<Event> events2 = (List<Event>) eventService.getAll();
        log.info("Found events: {}", events2.stream().map(Object::toString).collect(Collectors.joining(",")));

        //view tickets air dates and times
        Set<Ticket> byEventAndTime = ticketService.getByEventAndTime(event, airDateTime);
        log.info("Found tickets for events: {}", byEventAndTime.stream().map(Object::toString).collect(Collectors.joining(",")));


        //get ticket price

        double ticketsPrice = bookingService.getTicketsPrice(event, airDateTime, user, seats);
        log.info("Found tickets price: {}", ticketsPrice);

        // buy tickets

        Ticket ticket1 = new Ticket(user, event, airDateTime, 15, false);
        Ticket ticket2 = new Ticket(user, event, airDateTime, 14, false);
        Ticket ticket3 = new Ticket(user, event, airDateTime, 13, false);

        Set<Ticket> tickets = new HashSet<>();

        tickets.add(ticket1);
        tickets.add(ticket2);
        tickets.add(ticket3);

        for (Ticket t : tickets) {
            ticketService.save(t);
        }

        bookingService.bookTickets(tickets);

        log.info("Booked tickets: {}", bookingService.getPurchasedTicketsForEvent(event, airDateTime).stream()
                .map(Object::toString).collect(Collectors.joining("\n")));


        ctx.close();
    }
}
