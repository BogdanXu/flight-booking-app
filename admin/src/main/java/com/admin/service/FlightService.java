package com.admin.service;

import com.admin.dto.FlightDTO;
import com.admin.mapper.DestinationMapper;
import com.admin.mapper.FlightMapper;
import com.admin.mapper.OperatorMapper;
import com.admin.model.Flight;
import com.admin.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public Flight createFlight(FlightDTO flightDTO) {
        Flight flight = FlightMapper.toEntity(flightDTO);
        return flightRepository.save(flight);
    }

    public List<FlightDTO> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        return flights.stream()
                .map(FlightMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    public FlightDTO updateFlight(Long id, FlightDTO flightDTO) {
        Optional<Flight> optionalFlight = flightRepository.findById(id);
        if (optionalFlight.isPresent()) {
            Flight flight = optionalFlight.get();
            flight.setFlightCode(flightDTO.getFlightCode());
            flight.setDate(flightDTO.getDate());
            flight.setTicketPrice(flightDTO.getTicketPrice());
            flight.setSeatsAvailable(flightDTO.getSeatsAvailable());
            flight.setFlightDuration(flightDTO.getFlightDuration());
            flight.setOperator(OperatorMapper.toEntity(flightDTO.getOperator()));
            flight.setDepartureAirport(DestinationMapper.toEntity(flightDTO.getDepartureAirport()));
            flight.setArrivalAirport(DestinationMapper.toEntity(flightDTO.getArrivalAirport()));
            return FlightMapper.toDTO(flightRepository.save(flight));
        } else {
            return null;
        }
    }

}
