package ua.com.spring.core.test.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event extends DomainObject {

    private String name;

    private NavigableSet<LocalDateTime> airDates = new TreeSet<>();

    private double basePrice;

    private EventRating rating;

    private NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();

    public Event(String name, double basePrice, EventRating rating) {
        this.name = name;
        this.basePrice = basePrice;
        this.rating = rating;
    }
}
