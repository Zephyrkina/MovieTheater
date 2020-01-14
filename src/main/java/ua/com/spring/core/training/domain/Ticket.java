package ua.com.spring.core.training.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket extends DomainObject implements Comparable<Ticket> {

    private User user;

    private Event event;

    private LocalDateTime dateTime;

    private Long seat;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        if (!super.equals(o)) return false;
        Ticket ticket = (Ticket) o;
        return event.equals(ticket.event) &&
                dateTime.equals(ticket.dateTime) &&
                seat.equals(ticket.seat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), event, dateTime, seat);
    }

    @Override
    public int compareTo(Ticket other) {
        if (other == null) {
            return 1;
        }
        int result = dateTime.compareTo(other.getDateTime());

        if (result == 0) {
            result = event.getName().compareTo(other.getEvent().getName());
        }
        if (result == 0) {
            result = Long.compare(seat, other.getSeat());
        }
        return result;
    }

}
