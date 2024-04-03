package com.admin.repository;

import com.admin.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    public List<Flight> findAllByDepartureAirportCodAirportAndArrivalAirportCodAirport(String departure, String arrival);
}
