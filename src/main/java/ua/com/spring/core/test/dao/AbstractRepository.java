package ua.com.spring.core.test.dao;

import java.util.List;
import java.util.Optional;

public interface AbstractRepository<T> {

    List<T> getAll();

    Optional<T> getById(Long id);

    T save(T object);

    T remove(T object);

    default T update(T object) {
        throw new UnsupportedOperationException();
    }

}
