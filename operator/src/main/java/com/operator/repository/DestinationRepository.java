package com.operator.repository;

import com.operator.model.Destination;
import org.springframework.stereotype.Repository;

import java.util.Optional;


import org.springframework.data.mongodb.repository.MongoRepository;


@Repository
public interface DestinationRepository extends MongoRepository<Destination, Long> {
    boolean existsByCodAirport(String codAirport);
    Optional<Destination> findByCodAirport(String departureAirportCode);
}
