package com.example.booking.controller;

import com.example.booking.dto.BookingDTO;
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
    public Mono<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        return bookingService.saveBooking(bookingDTO);
    }

    @GetMapping("/{id}")
    public Mono<BookingDTO> getBookingById(@PathVariable String id) {
        return bookingService.findById(id);
    }
}