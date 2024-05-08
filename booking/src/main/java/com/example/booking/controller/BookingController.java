package com.example.booking.controller;

import com.example.booking.dto.BookingDTO;
import com.example.booking.dto.ExtendedBookingDTO;
import com.example.booking.service.BookingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_user')")
    public Mono<BookingDTO> createBooking(@RequestBody @Valid ExtendedBookingDTO bookingDTO) {
        return bookingService.saveBooking(bookingDTO);
    }

    @GetMapping("/{id}")
    public Mono<BookingDTO> getBookingById(@PathVariable String id) {
        return bookingService.findById(id);
    }
}