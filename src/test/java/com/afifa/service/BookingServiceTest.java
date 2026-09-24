package com.afifa.service;

import com.afifa.dto.BookingDTO;
import com.afifa.exception.BookingNotFoundException;
import com.afifa.exception.EventNotFoundException;
import com.afifa.exception.InsufficientTicketsException;
import com.afifa.exception.UserNotFoundException;
import com.afifa.model.Booking;
import com.afifa.model.Event;
import com.afifa.model.User;
import com.afifa.repository.BookingRepository;
import com.afifa.repository.EventRepository;
import com.afifa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private BookingService bookingService;
    @Test
    void createBooking_shouldCreateBookingAndReduceTickets() {

        Event event = new Event();
        event.setId(1L);
        event.setName("AI Conference");
        event.setAvailableTickets(100);

        User user = new User();
        user.setId(1L);
        user.setName("Afifa");

        Booking booking = new Booking();
        booking.setNumberOfTickets(3);
        booking.setEvent(event);
        booking.setUser(user);

        when(eventRepository.findById(1L))
                .thenReturn(Optional.of(event));

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(bookingRepository.save(booking))
                .thenReturn(booking);

        Booking result = bookingService.createBooking(booking);

        assertEquals(3, result.getNumberOfTickets());
        assertEquals(97, event.getAvailableTickets());

        verify(eventRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).save(event);
        verify(bookingRepository, times(1)).save(booking);
    }
    @Test
    void createBooking_shouldThrowExceptionWhenTicketsAreInsufficient() {

        Event event = new Event();
        event.setId(1L);
        event.setAvailableTickets(5);

        User user = new User();
        user.setId(1L);

        Booking booking = new Booking();
        booking.setEvent(event);
        booking.setUser(user);
        booking.setNumberOfTickets(10);

        when(eventRepository.findById(1L))
                .thenReturn(Optional.of(event));

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        assertThrows(
                InsufficientTicketsException.class,
                () -> bookingService.createBooking(booking)
        );

        verify(eventRepository, never()).save(any(Event.class));
        verify(bookingRepository, never()).save(any(Booking.class));
    }
    @Test
    void createBooking_shouldThrowExceptionWhenEventNotFound() {

        Event event = new Event();
        event.setId(1L);

        User user = new User();
        user.setId(1L);

        Booking booking = new Booking();
        booking.setEvent(event);
        booking.setUser(user);
        booking.setNumberOfTickets(2);

        when(eventRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                EventNotFoundException.class,
                () -> bookingService.createBooking(booking)
        );

        verify(userRepository, never()).findById(anyLong());
        verify(bookingRepository, never()).save(any(Booking.class));
    }
    @Test
    void createBooking_shouldThrowExceptionWhenUserNotFound() {

        Event event = new Event();
        event.setId(1L);
        event.setAvailableTickets(100);

        User user = new User();
        user.setId(1L);

        Booking booking = new Booking();
        booking.setEvent(event);
        booking.setUser(user);
        booking.setNumberOfTickets(2);

        when(eventRepository.findById(1L))
                .thenReturn(Optional.of(event));

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> bookingService.createBooking(booking)
        );

        verify(eventRepository, never()).save(any(Event.class));
        verify(bookingRepository, never()).save(any(Booking.class));
    }
    @Test
    void getBookingDTO_shouldReturnBookingDTO() {

        User user = new User();
        user.setId(1L);
        user.setName("Afifa");

        Event event = new Event();
        event.setId(10L);
        event.setName("AI Conference");

        Booking booking = new Booking();
        booking.setBookingId(5L);
        booking.setUser(user);
        booking.setEvent(event);
        booking.setNumberOfTickets(3);

        when(bookingRepository.findById(5L))
                .thenReturn(Optional.of(booking));

        BookingDTO result = bookingService.getBookingDTO(5L);

        assertEquals(5L, result.getBookingId());
        assertEquals(1L, result.getUserId());
        assertEquals("Afifa", result.getUserName());
        assertEquals(10L, result.getEventId());
        assertEquals("AI Conference", result.getEventName());
        assertEquals(3, result.getNumberOfTickets());

        verify(bookingRepository, times(1)).findById(5L);
    }
    @Test
    void getBookingDTO_shouldThrowExceptionWhenBookingNotFound() {

        when(bookingRepository.findById(5L))
                .thenReturn(Optional.empty());

        assertThrows(
                BookingNotFoundException.class,
                () -> bookingService.getBookingDTO(5L)
        );

        verify(bookingRepository, times(1)).findById(5L);
    }
    @Test
    void cancelBooking_shouldRestoreTicketsAndDeleteBooking() {

        Event event = new Event();
        event.setId(10L);
        event.setAvailableTickets(97);

        Booking booking = new Booking();
        booking.setBookingId(5L);
        booking.setEvent(event);
        booking.setNumberOfTickets(3);

        when(bookingRepository.findById(5L))
                .thenReturn(Optional.of(booking));

        when(eventRepository.findById(10L))
                .thenReturn(Optional.of(event));

        bookingService.cancelBooking(5L);

        assertEquals(100, event.getAvailableTickets());

        verify(eventRepository, times(1)).save(event);
        verify(bookingRepository, times(1)).delete(booking);
    }
    @Test
    void cancelBooking_shouldThrowExceptionWhenBookingNotFound() {

        when(bookingRepository.findById(5L))
                .thenReturn(Optional.empty());

        assertThrows(
                BookingNotFoundException.class,
                () -> bookingService.cancelBooking(5L)
        );

        verify(eventRepository, never()).save(any(Event.class));
        verify(bookingRepository, never()).delete(any(Booking.class));
    }
    @Test
    void updateBooking_shouldUpdateTickets() {

        Event event = new Event();
        event.setId(10L);
        event.setAvailableTickets(10);

        Booking booking = new Booking();
        booking.setBookingId(5L);
        booking.setEvent(event);
        booking.setNumberOfTickets(2);

        Booking updatedBooking = new Booking();
        updatedBooking.setNumberOfTickets(5);

        when(bookingRepository.findById(5L))
                .thenReturn(Optional.of(booking));

        when(eventRepository.findById(10L))
                .thenReturn(Optional.of(event));

        when(bookingRepository.save(booking))
                .thenReturn(booking);

        Booking result = bookingService.updateBooking(5L, updatedBooking);

        assertEquals(5, result.getNumberOfTickets());
        assertEquals(7, event.getAvailableTickets());

        verify(eventRepository).save(event);
        verify(bookingRepository).save(booking);
    }
    @Test
    void updateBooking_shouldThrowExceptionWhenTicketsAreInsufficient() {

        Event event = new Event();
        event.setId(10L);
        event.setAvailableTickets(2);

        Booking booking = new Booking();
        booking.setBookingId(5L);
        booking.setEvent(event);
        booking.setNumberOfTickets(3);

        Booking updatedBooking = new Booking();
        updatedBooking.setNumberOfTickets(10);

        when(bookingRepository.findById(5L))
                .thenReturn(Optional.of(booking));

        when(eventRepository.findById(10L))
                .thenReturn(Optional.of(event));

        assertThrows(
                InsufficientTicketsException.class,
                () -> bookingService.updateBooking(5L, updatedBooking)
        );

        verify(eventRepository, never()).save(any(Event.class));
        verify(bookingRepository, never()).save(any(Booking.class));
    }
}
