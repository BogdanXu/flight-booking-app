package com.admin.service;

import com.admin.dto.FlightDTO;
import com.admin.mapper.FlightMapper;
import com.admin.mapper.OperatorMapper;
import com.admin.model.Flight;
import com.admin.model.Operator;
import com.admin.repository.FlightRepository;
import com.admin.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService {


    private final FlightRepository flightRepository;

    private final FlightMapper flightMapper;


    private final DestinationService destinationService;

    private final OperatorRepository operatorRepository;

    public FlightService(FlightRepository flightRepository, FlightMapper flightMapper, DestinationService destinationService, OperatorRepository operatorRepository) {
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
        this.destinationService = destinationService;
        this.operatorRepository = operatorRepository;
    }

    public Flight createFlight(FlightDTO flightDTO) {
        Flight flight = flightMapper.toEntity(flightDTO);
        return flightRepository.save(flight);
    }

    public List<FlightDTO> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        return flights.stream()
                .map(FlightMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<FlightDTO> getAllFlightsByDepartureAndArrival(String departure, String arrival) {
        List<Flight> flights = flightRepository.findAllByDepartureAirportCodAirportAndArrivalAirportCodAirport(departure, arrival);
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
            Operator operator = operatorRepository.findById(flightDTO.getOperatorId()).get();
            flight.setOperator(operator);
            flight.setDepartureAirport(destinationService.findDestinationByAirportCode(flightDTO.getDepartureAirport()));
            flight.setArrivalAirport(destinationService.findDestinationByAirportCode(flightDTO.getArrivalAirport()));
            return FlightMapper.toDTO(flightRepository.save(flight));
        } else {
            return null;
        }
    }

}
