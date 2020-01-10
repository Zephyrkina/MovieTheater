package ua.com.spring.core.test.service;

import ua.com.spring.core.test.domain.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface EventService extends AbstractDomainObjectService<Event> {

    @Nullable Event getByName(@Nonnull String name);

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
