package com.operator.service;

import com.operator.dto.FlightDTO;
import com.operator.exception.DestinationNotFoundException;
import com.operator.exception.FlightNotFoundException;
import com.operator.model.Destination;
import com.operator.model.Flight;
import com.operator.repository.DestinationRepository;
import com.operator.repository.FlightRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final DestinationRepository destinationRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository, DestinationRepository destinationRepository) {
        this.flightRepository = flightRepository;
        this.destinationRepository = destinationRepository;
    }

    public List<FlightDTO> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        return flights.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public FlightDTO getFlightById(Long id) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            return convertToDTO(flightOptional.get());
        }
        throw new FlightNotFoundException(id);
    }

    @Transactional
    public FlightDTO createFlight(FlightDTO flightDTO) {
        validateAirportCodes(flightDTO);
        Flight flight = convertToEntity(flightDTO);
        Flight savedFlight = flightRepository.save(flight);
        return convertToDTO(savedFlight);
    }

    public FlightDTO updateFlight(Long id, FlightDTO flightDTO) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            Flight existingFlight = flightOptional.get();

            if (flightDTO.getFlightCode() != null) {
                existingFlight.setFlightCode(flightDTO.getFlightCode());
            }
            if (flightDTO.getDate() != null) {
                existingFlight.setDate(flightDTO.getDate());
            }
            if (flightDTO.getTicketPrice() != null) {
                existingFlight.setTicketPrice(flightDTO.getTicketPrice());
            }
            if (flightDTO.getSeatsAvailable() != null) {
                existingFlight.setSeatsAvailable(flightDTO.getSeatsAvailable());
            }
            if (flightDTO.getFlightDuration() != null) {
                existingFlight.setFlightDuration(flightDTO.getFlightDuration());
            }
            if (flightDTO.getDepartureAirportCode() != null) {
                Destination departureDestination = destinationRepository.findByCodAirport(flightDTO.getDepartureAirportCode())
                        .orElseThrow(() -> new DestinationNotFoundException("Destination with code " + flightDTO.getDepartureAirportCode() + " not found."));
                existingFlight.setDepartureAirport(departureDestination);
            }
            if (flightDTO.getArrivalAirportCode() != null) {
                Destination arrivalDestination = destinationRepository.findByCodAirport(flightDTO.getArrivalAirportCode())
                        .orElseThrow(() -> new DestinationNotFoundException("Destination with code " + flightDTO.getArrivalAirportCode() + " not found."));
                existingFlight.setArrivalAirport(arrivalDestination);
            }
            Flight updatedFlight = flightRepository.save(existingFlight);
            return convertToDTO(updatedFlight);
        }
        throw new FlightNotFoundException(id);
    }

    public void deleteFlight(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new FlightNotFoundException(id);
        }
        flightRepository.deleteById(id);
    }

    private FlightDTO convertToDTO(Flight flight) {
        return new FlightDTO(
                flight.getId(),
                flight.getFlightCode(),
                flight.getDate(),
                flight.getTicketPrice(),
                flight.getSeatsAvailable(),
                flight.getFlightDuration(),
                flight.getDepartureAirport().getCodAirport(),
                flight.getArrivalAirport().getCodAirport()
        );
    }

    private Flight convertToEntity(FlightDTO flightDTO) {
        Destination departureDestination = destinationRepository.findByCodAirport(flightDTO.getDepartureAirportCode())
                .orElseThrow(() -> new DestinationNotFoundException("Destination with code " + flightDTO.getDepartureAirportCode() + " not found."));

        Destination arrivalDestination = destinationRepository.findByCodAirport(flightDTO.getArrivalAirportCode())
                .orElseThrow(() -> new DestinationNotFoundException("Destination with code " + flightDTO.getArrivalAirportCode() + " not found."));

        return new Flight(
                flightDTO.getId(),
                flightDTO.getFlightCode(),
                flightDTO.getDate(),
                flightDTO.getTicketPrice(),
                flightDTO.getSeatsAvailable(),
                flightDTO.getFlightDuration(),
                departureDestination,
                arrivalDestination
        );
    }


    private void validateAirportCodes(FlightDTO flightDTO) {
        String departureAirportCode = flightDTO.getDepartureAirportCode();
        String arrivalAirportCode = flightDTO.getArrivalAirportCode();

        if (!destinationRepository.existsByCodAirport(departureAirportCode)) {
            throw new DestinationNotFoundException("Destination with code " + departureAirportCode + " not found.");
        }

        if (!destinationRepository.existsByCodAirport(arrivalAirportCode)) {
            throw new DestinationNotFoundException("Destination with code " + arrivalAirportCode + " not found.");
        }
    }
}