package com.example.booking.mappers;

import com.example.booking.dto.FlightDTO;
import com.example.booking.model.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {

    private FlightMapper() {
    }

    public static FlightDTO toDTO(Flight flight) {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setId(flight.getId());
        flightDTO.setFlightCode(flight.getFlightCode());
        flightDTO.setDate(flight.getFlightDate());
        flightDTO.setTicketPrice(flight.getTicketPrice());
        flightDTO.setSeatsAvailable(flight.getSeatsAvailable());
        flightDTO.setFlightDuration(flight.getFlightDuration());
        flightDTO.setDepartureAirportCode(flight.getDepartureAirportCode());
        flightDTO.setArrivalAirportCode(flight.getArrivalAirportCode());
        return flightDTO;
    }

    public static Flight fromDTO(FlightDTO flightDTO) {
        Flight flight = new Flight();
        flight.setId(flightDTO.getId());
        flight.setFlightCode(flightDTO.getFlightCode());
        flight.setFlightDate(flightDTO.getDate());
        flight.setTicketPrice(flightDTO.getTicketPrice());
        flight.setSeatsAvailable(flightDTO.getSeatsAvailable());
        flight.setFlightDuration(flightDTO.getFlightDuration());
        flight.setDepartureAirportCode(flightDTO.getDepartureAirportCode());
        flight.setArrivalAirportCode(flightDTO.getArrivalAirportCode());
        return flight;
    }
}