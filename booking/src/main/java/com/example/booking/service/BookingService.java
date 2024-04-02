package com.example.booking.service;

import com.example.booking.model.Booking;
import com.example.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Mono<Booking> saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Mono<Booking> findById(String id) {
        return bookingRepository.findById(id);
    }
}