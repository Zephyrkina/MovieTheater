package ua.com.spring.core.test.dao;

import ua.com.spring.core.test.domain.Auditorium;

import java.util.Map;

@Deprecated
public class AuditoriumRepository {
    public static Map<Long, Auditorium> auditoriums;

    static {
        auditoriums.put(1L, new Auditorium());
        auditoriums.put(2L, new Auditorium());
        auditoriums.put(3L, new Auditorium());

    }

}
