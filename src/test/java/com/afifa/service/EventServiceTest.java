package com.afifa.service;


import com.afifa.dto.CreateEventRequestDTO;
import com.afifa.dto.EventResponseDTO;
import com.afifa.exception.EventNotFoundException;
import com.afifa.model.Event;
import com.afifa.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock
   private EventRepository eventRepository;
    @InjectMocks
    private EventService eventService;

    @Test
    void getEventById_shouldReturnEvent() {

        Event event = new Event();
        event.setId(1L);
        event.setName("AI Conference");

        when(eventRepository.findById(1L))
                .thenReturn(Optional.of(event));

        EventResponseDTO result = eventService.getEventById(1L);

        assertEquals("AI Conference", result.getName());
    }
    @Test
    void getEventById_shouldThrowExceptionWhenEventNotFound() {

        when(eventRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                EventNotFoundException.class,
                () -> eventService.getEventById(1L)
        );
    }@Test
    void createEvent_shouldCreateAndReturnEvent() {

        CreateEventRequestDTO dto = new CreateEventRequestDTO();
        dto.setName("AI Conference");
        dto.setAvailableTickets(100);
        Event savedEvent = new Event();
        savedEvent.setId(1L);
        savedEvent.setName("AI Conference");

        when(eventRepository.save(any(Event.class)))
                .thenReturn(savedEvent);

        EventResponseDTO result = eventService.createEvent(dto);

        assertEquals("AI Conference", result.getName());
        verify(eventRepository, times(1)).save(any(Event.class));
    }
}
