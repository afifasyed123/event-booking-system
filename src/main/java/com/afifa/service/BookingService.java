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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService
{
        private final UserRepository userRepository;
        private final BookingRepository repository;
        private final EventRepository eventRepository;
        public BookingService(UserRepository userRepository, BookingRepository repository, EventRepository eventRepository) {
            this.userRepository = userRepository;
            this.repository = repository;
            this.eventRepository = eventRepository;
        }
        public Booking createBooking(Booking booking){
          Event event=  eventRepository.findById(booking.getEvent().getId()).orElseThrow(()-> new EventNotFoundException("Event not Found"));
            User user = userRepository.findById(booking.getUser().getId())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            if(event.getAvailableTickets()<booking.getNumberOfTickets()){
                throw new InsufficientTicketsException("Not enough tickets available.Try later");
            }
            event.setAvailableTickets(event.getAvailableTickets()- booking.getNumberOfTickets());
            booking.setEvent(event);
            booking.setUser(user);
            eventRepository.save(event);
            return repository.save(booking);
        }
        public Booking getBookingById(Long id){
            return repository.findById(id).orElseThrow(
                    ()-> new BookingNotFoundException("Booking not found!")
            );
        }
    public List<BookingDTO> getAllBookingDTOs() {
        return repository.findAll()
                .stream()
                .map(booking -> new BookingDTO(
                        booking.getBookingId(),
                        booking.getUser().getId(),
                        booking.getUser().getName(),
                        booking.getEvent().getId(),
                        booking.getEvent().getName(),
                        booking.getNumberOfTickets()
                ))
                .toList();
    }

    public void cancelBooking(Long id) {

        Booking booking = repository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found!"));

        Event event = eventRepository.findById(booking.getEvent().getId())
                .orElseThrow(() -> new EventNotFoundException("Event not found!"));

        event.setAvailableTickets(
                event.getAvailableTickets() + booking.getNumberOfTickets()
        );

        eventRepository.save(event);
        repository.delete(booking);
    }
    public Booking updateBooking(Long id, Booking updatedBooking) {

        Booking booking = repository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found!"));

        Event event = eventRepository.findById(booking.getEvent().getId())
                .orElseThrow(() -> new EventNotFoundException("Event not found!"));

        int oldTickets = booking.getNumberOfTickets();
        int newTickets = updatedBooking.getNumberOfTickets();

        int difference = newTickets - oldTickets;

        if (difference > event.getAvailableTickets()) {
            throw new InsufficientTicketsException("Not enough tickets available!");
        }

        event.setAvailableTickets(event.getAvailableTickets() - difference);

        booking.setNumberOfTickets(newTickets);

        eventRepository.save(event);

        return repository.save(booking);
    }
    public BookingDTO getBookingDTO(Long id){
            Booking booking = repository.findById(id).orElseThrow(
                    ()->new BookingNotFoundException("Booking not found")
            );
            return new BookingDTO(
                    booking.getBookingId(),
                    booking.getUser().getId(),
                    booking.getUser().getName(),
                    booking.getEvent().getId(),
                    booking.getEvent().getName(),
                    booking.getNumberOfTickets()
            );
    }

}

