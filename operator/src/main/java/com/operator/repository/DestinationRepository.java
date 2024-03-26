package com.operator.repository;

import com.operator.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    boolean existsByCodAirport(String codAirport);
    Optional<Destination> findByCodAirport(String departureAirportCode);
}
