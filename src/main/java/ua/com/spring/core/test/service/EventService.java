package ua.com.spring.core.test.service;

import ua.com.spring.core.test.domain.Auditorium;
import ua.com.spring.core.test.domain.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public interface EventService extends AbstractDomainObjectService<Event> {

    @Nullable Event getByName(@Nonnull String name);

    boolean addAirDateTime(Event event, LocalDateTime dateTime, Auditorium auditorium);

    boolean removeAirDateTime(Event event, LocalDateTime dateTime);



        /*   @Nonnull Set<Event> getForDateRange(@Nonnull LocalDate from, @Nonnull LocalDate to);

    *//*
     * Return events from 'now' till the the specified date time
     * 
     * @param to End date time inclusive
     * s
     * @return Set of events
     *//*
    @Nonnull Set<Event> getNextEvents(@Nonnull LocalDateTime to);*/

}
