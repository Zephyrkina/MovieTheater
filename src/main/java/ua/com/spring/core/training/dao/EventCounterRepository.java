package ua.com.spring.core.training.dao;

import ua.com.spring.core.training.domain.EventCounter;

import java.util.List;

public interface EventCounterRepository extends AbstractRepository<EventCounter> {

    default List<EventCounter> getAll() { throw new UnsupportedOperationException();}

    default EventCounter remove(EventCounter object) { throw new UnsupportedOperationException(); }

}
