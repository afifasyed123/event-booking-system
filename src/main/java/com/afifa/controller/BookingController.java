package com.afifa.controller;

import com.afifa.dto.BookingDTO;
import com.afifa.model.Booking;
import com.afifa.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @PostMapping
    public Booking createBooking(@RequestBody Booking booking){
        return bookingService.createBooking(booking);
    }

    @GetMapping("/{id}")
    public BookingDTO getBookingById(@PathVariable Long id){
        return bookingService.getBookingDTO(id);
    }
    @GetMapping
    public List<BookingDTO> getAllBookings(){
        return bookingService.getAllBookingDTOs();
    }

    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable Long id,@RequestBody Booking booking){
        return bookingService.updateBooking(id,booking);
    }
    @DeleteMapping("/{id}")
    public void cancelBooking(@PathVariable  Long id){
        bookingService.cancelBooking(id);
    }
}
