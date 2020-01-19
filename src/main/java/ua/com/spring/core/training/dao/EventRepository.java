package ua.com.spring.core.training.dao;

import ua.com.spring.core.training.domain.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends AbstractRepository<Event> {
     Optional<Event> getByName(String name);

    }
