package ua.com.spring.core.test.dao;

import java.util.List;
import java.util.Optional;

public interface AbstractRepository<T> {
    List<T> getAll();
    Optional<T> getById(Long id);
    T save(T object);
    T remove(T object);
    T update(T object);

}
