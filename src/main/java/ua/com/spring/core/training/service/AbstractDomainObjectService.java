package ua.com.spring.core.training.service;

import java.util.Collection;

import javax.annotation.Nonnull;

import ua.com.spring.core.training.domain.DomainObject;

public interface AbstractDomainObjectService<T extends DomainObject> {

    T save(@Nonnull T object);

    void remove(@Nonnull T object);

    T getById(@Nonnull Long id);

    @Nonnull Collection<T> getAll();

    default T update(T object) {
        throw new UnsupportedOperationException();
    }

}
