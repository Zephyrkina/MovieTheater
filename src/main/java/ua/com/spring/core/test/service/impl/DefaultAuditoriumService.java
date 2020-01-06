package ua.com.spring.core.test.service.impl;

import org.springframework.stereotype.Service;
import ua.com.spring.core.test.domain.Auditorium;
import ua.com.spring.core.test.service.AuditoriumService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class DefaultAuditoriumService implements AuditoriumService {

    private Set<Auditorium> auditoriums;

    public DefaultAuditoriumService(Set<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return new HashSet<>(auditoriums);
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return auditoriums.stream().filter(auditorium -> auditorium.getName().equals(name))
                .findFirst().orElseThrow(() -> new RuntimeException("No auditorium with such name found"));
    }
}
