package ua.com.spring.core.training.service.impl;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.spring.core.training.domain.Auditorium;
import ua.com.spring.core.training.exceptions.AuditoriumNotFound;
import ua.com.spring.core.training.service.AuditoriumService;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:application-test-context.xml"})
class DefaultAuditoriumServiceTest {

    @Autowired
    private AuditoriumService auditoriumService;

    @Test
    void whenGetAll_thenReturnAuditoriums() {
        int expectedNumberOfAuditoriums = 3;

        Set<Auditorium> actualAuditoriums = auditoriumService.getAll();

        assertThat(actualAuditoriums.size(), equalTo(expectedNumberOfAuditoriums));
    }

    @Test
    void whenGetByExistingName_thenReturnAuditorium() {
        String expectedAuditoriumName = "Red";

        Auditorium auditorium = auditoriumService.getByName(expectedAuditoriumName);

        assertThat(auditorium.getName(), equalTo(expectedAuditoriumName));
    }

    @Test
    void whenGetByNonExistingName_thenThrowAuditoriumNotFoundException() {
        String notExistingAuditoriumName = "White";
        assertThrows(AuditoriumNotFound.class, () -> { auditoriumService.getByName(notExistingAuditoriumName);});
    }

    @Test
    void whenGetByEmptyName_thenThrowException() {
        String nullAuditoriumName = null;
        assertThrows(AuditoriumNotFound.class, () -> { auditoriumService.getByName(nullAuditoriumName);});
    }


    @Test
    void whenCountVipSeats_thenReturnCorrectNumber() {
        Set<Long> seats = Sets.newHashSet(38L, 39L, 40L, 41L);
        String testAuditoriumName = "Red";
        Auditorium redAuditorium = auditoriumService.getByName(testAuditoriumName);
        long expectVipSeatsCount = 2l;

        long actualVipSeatsCount = auditoriumService.countVipSeats(seats, redAuditorium);

        assertThat(actualVipSeatsCount, equalTo(expectVipSeatsCount));
    }

    @Test
    void givenEmptySeatsSet_whenCountVipSeats_thenThrowIllegalArgumentException() {
        Set<Long> seats = Sets.newHashSet();
        String testAuditoriumName = "Red";
        Auditorium redAuditorium = auditoriumService.getByName(testAuditoriumName);

        assertThrows(IllegalArgumentException.class, () -> { auditoriumService.countVipSeats(seats, redAuditorium); });
    }

    @Test
    void givenNullSeatsSet_whenCountVipSeats_thenThrowIllegalArgumentException() {
        Set<Long> seats = null;
        String testAuditoriumName = "Red";
        Auditorium redAuditorium = auditoriumService.getByName(testAuditoriumName);

        assertThrows(IllegalArgumentException.class, () -> { auditoriumService.countVipSeats(seats, redAuditorium); });
    }

    @Test
    void givenNullAuditorium_whenCountVipSeats_thenThrowIllegalArgumentException() {
        Set<Long> seats = Sets.newHashSet(38L, 39L, 40L, 41L);
        Auditorium testAuditorium = null;

        assertThrows(IllegalArgumentException.class, () -> { auditoriumService.countVipSeats(seats, testAuditorium); });
    }
}