package com.admin.mapper;

import com.admin.dto.FlightDTO;
import com.admin.model.Flight;
import com.admin.model.Operator;
import com.admin.repository.OperatorRepository;
import com.admin.service.DestinationService;
import com.admin.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {
    private final OperatorRepository operatorRepository;
    private final DestinationService destinationService;

    public FlightMapper(OperatorRepository operatorRepository, DestinationService destinationService) {
        this.operatorRepository = operatorRepository;
        this.destinationService = destinationService;
    }

    public static FlightDTO toDTO(Flight flight) {
        if (flight == null) {
            return null;
        }

        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setId(flight.getId());
        flightDTO.setFlightCode(flight.getFlightCode());
        flightDTO.setDate(flight.getDate());
        flightDTO.setTicketPrice(flight.getTicketPrice());
        flightDTO.setSeatsAvailable(flight.getSeatsAvailable());
        flightDTO.setFlightDuration(flight.getFlightDuration());
        flightDTO.setOperatorId(OperatorMapper.toDTO(flight.getOperator()).getId());
        flightDTO.setDepartureAirport(DestinationMapper.toDTO(flight.getDepartureAirport()).getCodAirport());
        flightDTO.setArrivalAirport(DestinationMapper.toDTO(flight.getArrivalAirport()).getCodAirport());

        return flightDTO;
    }

    public Flight toEntity(FlightDTO flightDTO) {
        if (flightDTO == null) {
            return null;
        }

        Flight flight = new Flight();
        flight.setId(flightDTO.getId());
        flight.setFlightCode(flightDTO.getFlightCode());
        flight.setDate(flightDTO.getDate());
        flight.setTicketPrice(flightDTO.getTicketPrice());
        flight.setSeatsAvailable(flightDTO.getSeatsAvailable());
        flight.setFlightDuration(flightDTO.getFlightDuration());
        Operator operator = operatorRepository.findById(flightDTO.getOperatorId()).get();
        flight.setOperator(operator);
        flight.setDepartureAirport(destinationService.findDestinationByAirportCode(flightDTO.getDepartureAirport()));
        flight.setArrivalAirport(destinationService.findDestinationByAirportCode(flightDTO.getArrivalAirport()));

        return flight;
    }
}
