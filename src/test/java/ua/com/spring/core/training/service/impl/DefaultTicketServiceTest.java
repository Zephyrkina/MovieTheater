package ua.com.spring.core.training.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.spring.core.training.dao.TicketRepository;
import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.EventRating;
import ua.com.spring.core.training.domain.Ticket;
import ua.com.spring.core.training.exceptions.TicketNotFoundException;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultTicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private DefaultTicketService ticketService;

    @Test
    void whenGetByExistingEventAndTime_shouldReturnTicket() {
        Event event = new Event("mon", 10.0, EventRating.MID );
        LocalDateTime airDateTime = LocalDateTime.now();
        Set<Ticket> expectedTickets = createTickets(3);

        when(ticketRepository.getByEventAndTime(event,airDateTime)).thenReturn( expectedTickets);
        Set<Ticket> actualTickets = ticketService.getByEventAndTime(event, airDateTime);

        assertThat(actualTickets, equalTo(expectedTickets));
        assertThat(actualTickets.size(), equalTo(expectedTickets.size()));
        verify(ticketRepository).getByEventAndTime(event, airDateTime);
    }

    @Test
    void givenEvent_andNonExistingAirDate_whenGetByEventAndTime_shouldReturnEmptySet() {
        Event event = new Event("mon", 10.0, EventRating.MID );
        LocalDateTime airDateTime = LocalDateTime.now();
        Set<Ticket> expectedTickets = Sets.newHashSet();

        when(ticketRepository.getByEventAndTime(event,airDateTime)).thenReturn(Sets.newHashSet());
        Set<Ticket> actualTickets = ticketService.getByEventAndTime(event, airDateTime);

        assertThat(actualTickets, equalTo(expectedTickets));
        assertThat(actualTickets.size(), equalTo(expectedTickets.size()));
        verify(ticketRepository).getByEventAndTime(event, airDateTime);

    }

    @Test
    void givenEmptyEvent_whenGetByName_shouldThrowException() {
        Event event = null;
        LocalDateTime airDateTime = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () -> {ticketService.getByEventAndTime(event, airDateTime);});

        verify(ticketRepository, never()).getByEventAndTime(event, airDateTime);
    }

    @Test
    void givenValidTicket_whenSave_thenShouldSave() {
        Event event = new Event("mon", 10.0, EventRating.MID );
        LocalDateTime airDateTime = LocalDateTime.now();
        Ticket expectedTicket = new Ticket(null,  event, airDateTime, 10L);

        when(ticketRepository.save(expectedTicket)).thenReturn(expectedTicket);
        Ticket actualTicket = ticketRepository.save(expectedTicket);

        assertThat(actualTicket, equalTo(expectedTicket));
        verify(ticketRepository).save(expectedTicket);
    }

    @Test
    void givenNullTicket_whenSave_thenShouldThrowException() {
        Ticket expectedTicket = null;

        assertThrows(IllegalArgumentException.class, () -> {ticketService.save(expectedTicket);});

        verify(ticketRepository, never()).save(expectedTicket);
    }

    @Test
    void givenValidTicket_whenRemove_thenShouldRemove() {
        Event event = new Event("mon", 10.0, EventRating.MID );
        LocalDateTime airDateTime = LocalDateTime.now();
        Ticket expectedTicket = new Ticket(null,  event, airDateTime, 10L);

        when(ticketRepository.remove(expectedTicket)).thenReturn(expectedTicket);
        Ticket actualTicket = ticketRepository.remove(expectedTicket);

        assertThat(actualTicket, equalTo(expectedTicket));
        verify(ticketRepository).remove(expectedTicket);
    }

    @Test
    void givenNullTicket_whenRemove_thenShouldThrowException() {
        Ticket expectedTicket = null;

        assertThrows(IllegalArgumentException.class, () -> {ticketService.remove(expectedTicket);});

        verify(ticketRepository, never()).remove(expectedTicket);
    }

    @Test
    void givenExistingId_whenGetById_shouldReturnTicket() {
        Long existingId = 2L;
        Event event = new Event("mon", 10.0, EventRating.MID );
        LocalDateTime airDateTime = LocalDateTime.now();
        Ticket expectedTicket = new Ticket(null,  event, airDateTime, 10L);

        when(ticketRepository.getById(existingId)).thenReturn(Optional.of(expectedTicket));
        Ticket actualTicket = ticketService.getById(existingId);

        assertThat(actualTicket, equalTo(expectedTicket));
        verify(ticketRepository).getById(existingId);
    }

    @Test
    void givenNonExistingId_whenGetById_shouldThrowException() {
        Long nonExistingId = 223L;

        when(ticketRepository.getById(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(TicketNotFoundException.class, () -> {ticketService.getById(nonExistingId);});

        verify(ticketRepository).getById(nonExistingId);
    }

    @Test
    void givenNullId_whenGetById_shouldThrowException() {
        Long nullId = null;

        assertThrows(IllegalArgumentException.class, () -> {ticketService.getById(nullId);});

        verify(ticketRepository, never()).getById(nullId);
    }

    @Test
    void whenGetAll () {
        int expectedTicketsSize = 3;
        List<Ticket> expectedTickets = Lists.newArrayList(createTickets(expectedTicketsSize));

        when(ticketRepository.getAll()).thenReturn(expectedTickets);

        List<Ticket> actualTickets = ticketRepository.getAll();

        assertThat(actualTickets, equalTo(expectedTickets));
        assertThat(actualTickets.size(), equalTo(expectedTicketsSize));
        verify(ticketRepository).getAll();
    }

    private Set<Ticket> createTickets(int ticketQuantity) {
        Set<Ticket> ticketList = new HashSet<>();
        Event event = new Event("mon", 10.0, EventRating.MID );
        LocalDateTime airDateTime = LocalDateTime.now();

        for (int i = 0; i < ticketQuantity; i++) {
            Ticket ticket = new Ticket(null,  event, airDateTime, Long.valueOf(10*i+i));
            ticketList.add(ticket);
        }
        return ticketList;
    }

}