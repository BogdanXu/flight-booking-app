package com.example.booking.repository;

import com.example.booking.model.Booking;
import com.example.booking.model.BookingStatus;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends ReactiveMongoRepository<Booking, String> {

    public Mono<Booking> findBookingByFlightId();

    Flux<Booking> findBookingsByBookingStatusNotInAndExpirationDateBefore(List<BookingStatus> excludedStatuses, LocalDateTime expirationDate);

}
