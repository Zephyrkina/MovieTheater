package ua.com.spring.core.test.service;

import java.util.Collection;

import javax.annotation.Nonnull;

import ua.com.spring.core.test.domain.DomainObject;

public interface AbstractDomainObjectService<T extends DomainObject> {

    T save(@Nonnull T object);

    void remove(@Nonnull T object);

    T getById(@Nonnull Long id);

    @Nonnull Collection<T> getAll();

    T update(T object);
}
