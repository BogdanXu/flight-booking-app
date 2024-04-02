package com.example.booking.repository;

import com.example.booking.model.Booking;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface BookingRepository extends ReactiveMongoRepository<Booking, String> {

    public Mono<Booking> findBookingByFlightId();
}
