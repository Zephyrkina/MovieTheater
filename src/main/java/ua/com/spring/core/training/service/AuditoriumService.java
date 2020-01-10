package ua.com.spring.core.training.service;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.com.spring.core.training.domain.Auditorium;

public interface AuditoriumService{

    @Nonnull Set<Auditorium> getAll();

    @Nullable Auditorium getByName(@Nonnull String name);

}
