package com.example.booking.repository;

import com.example.booking.model.Booking;
import com.example.booking.model.BookingStatus;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface BookingRepository extends ReactiveMongoRepository<Booking, String> {

    public Mono<Booking> findBookingByFlightId();


    @Query("{'expirationDate': {$lt: ?0}, 'bookingStatus': {$ne: 'Success'}}")
    Mono<Void> updateBookingStatusToRejectedByExpirationDateBefore(LocalDateTime expirationDate);
}
