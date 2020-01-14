package ua.com.spring.core.training.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;
import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.EventRating;
import ua.com.spring.core.training.domain.Ticket;
import ua.com.spring.core.training.domain.User;
import ua.com.spring.core.training.service.BookingService;
import ua.com.spring.core.training.service.EventService;
import ua.com.spring.core.training.service.UserService;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SpringShellCommands implements CommandMarker {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @CliCommand(value = "hi", help = "say hello.")
    public String hi() {
        return "hello";
    }

    @CliCommand(value = "get-events")
    public String getAllEvents() {
        Collection<Event> allEvents = eventService.getAll();
        return allEvents.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    @CliCommand(value = "create-event")
    public String createEvent(@CliOption(key = "name") String eventName,
                              @CliOption(key = "price") String price) {

        Event event = Event.builder()
                .name(eventName)
                .basePrice(Double.parseDouble(price))
                .rating(EventRating.MID)
                .build();

        eventService.save(event);

        return "Done";
    }

    @CliCommand(value = "get-purchased-tickets")
    public String getPurchasedTicketsForEvent(
            @CliOption(key = "event-name") String eventName,
            @CliOption(key = "event-date") String eventDate) throws ParseException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(eventDate, formatter);

        Event event = eventService.getByName(eventName);
        Set<Ticket> purchasedTicketsForEvent = bookingService.getPurchasedTicketsForEvent(event, dateTime);
        return purchasedTicketsForEvent.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    @CliCommand(value = "create-user")
    public String registerUser(
            @CliOption(key = "first-name") String firstName,
            @CliOption(key = "last-name") String lastName,
            @CliOption(key = "email") String email,
            @CliOption(key = "birthday") String birthday) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdayDate = LocalDate.parse(birthday, formatter);

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .birthday(birthdayDate)
                .build();

        userService.save(user);

        return "Done";
    }

   /* @CliCommand(value = "get-event-by-date")
    public String getEventByDate(
            @CliOption(key = "date") String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdayDate = LocalDate.parse(date, formatter);
        
    }*/

    @CliCommand(value = "get-tickets-price")
    public String getTicketsPrice(
            @CliOption(key = "eventName") String eventName,
            @CliOption(key = "airDate") String airDate,
            @CliOption(key = "seat") String seat) {

        Event event = eventService.getByName(eventName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime date = LocalDateTime.parse(airDate, formatter);
        Set<Long> seats = new HashSet<>();
        seats.add(Long.parseLong(seat));

        double ticketsPrice = bookingService.getTicketsPrice(event, date, null, seats);
        return String.valueOf(ticketsPrice);

    }

    @CliCommand(value = "book-tickets")
    public String bookTickets(
            @CliOption(key = "eventName") String eventName,
            @CliOption(key = "airDate") String airDate,
            @CliOption(key = "seat") String seat) {

        Event event = eventService.getByName(eventName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime date = LocalDateTime.parse(airDate, formatter);

        Ticket ticket = Ticket.builder()
                .event(event)
                .dateTime(date)
                .seat(Long.parseLong(seat))
                .build();

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket);

        bookingService.bookTickets(tickets, null);

        return "Done";

    }
}