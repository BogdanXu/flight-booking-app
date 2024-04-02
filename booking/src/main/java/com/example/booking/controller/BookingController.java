package com.example.booking.controller;

import com.example.booking.model.Booking;
import com.example.booking.service.BookingService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Mono<Booking> createBooking(@RequestBody Booking booking) {
        return bookingService.saveBooking(booking);
    }

    @GetMapping("/{id}")
    public Mono<Booking> getBookingById(@PathVariable String id) {
        return bookingService.findById(id);
    }
}