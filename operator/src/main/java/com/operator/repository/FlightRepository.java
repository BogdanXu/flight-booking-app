package com.operator.repository;

import com.operator.model.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface FlightRepository extends ReactiveMongoRepository<Flight, Long> {
    @Query("{'departureAirport.codAirport': ?0, 'arrivalAirport.codAirport': ?1, 'flightDate': { $gte: ?2, $lte: ?3 }, 'flightCode': { $regex: ?4 } }")
    Flux<Flight> findByCriteria(String startDestination, String endDestination, LocalDate startDate, LocalDate endDate, String IATACode);
}
