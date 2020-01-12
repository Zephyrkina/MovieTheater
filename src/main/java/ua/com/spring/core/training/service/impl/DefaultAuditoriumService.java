package ua.com.spring.core.training.service.impl;

import ua.com.spring.core.training.domain.Auditorium;
import ua.com.spring.core.training.exceptions.AuditoriumNotFound;
import ua.com.spring.core.training.service.AuditoriumService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

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
                .findFirst().orElseThrow(() -> new AuditoriumNotFound("No auditorium with such name found"));
    }

    public long countVipSeats(Collection<Long> seats, Auditorium auditorium) {
        checkArgument(seats != null && !seats.isEmpty(), "Seats shouldn't be empty");
        checkArgument(auditorium != null, "Auditorium shouldn't be null");

        return seats.stream().filter(seat -> auditorium.getVipSeats().contains(seat)).count();
    }
}
