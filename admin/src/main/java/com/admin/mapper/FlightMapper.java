package com.admin.mapper;

import com.admin.dto.FlightDTO;
import com.admin.model.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {

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
        flightDTO.setOperator(OperatorMapper.toDTO(flight.getOperator()));
        flightDTO.setDepartureAirport(DestinationMapper.toDTO(flight.getDepartureAirport()));
        flightDTO.setArrivalAirport(DestinationMapper.toDTO(flight.getArrivalAirport()));

        return flightDTO;
    }

    public static Flight toEntity(FlightDTO flightDTO) {
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
        flight.setOperator(OperatorMapper.toEntity(flightDTO.getOperator()));
        flight.setDepartureAirport(DestinationMapper.toEntity(flightDTO.getDepartureAirport()));
        flight.setArrivalAirport(DestinationMapper.toEntity(flightDTO.getArrivalAirport()));

        return flight;
    }
}
