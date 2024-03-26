package com.operator.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.security.Timestamp;

public class FlightDTO {
    private Long id;

    @NotBlank(message = "Flight code must not be blank")
    private String flightCode;

    @NotNull(message = "Date must not be null")
    private Timestamp date;

    @NotBlank(message = "Ticket price must not be blank")
    private String ticketPrice;

    @NotNull(message = "Seats available must not be null")
    @Positive(message = "Seats available must be positive")
    private Integer seatsAvailable;

    @NotBlank(message = "Flight duration must not be blank")
    private String flightDuration;

    @NotBlank(message = "Departure airport code must not be blank")
    private String departureAirportCode;

    @NotBlank(message = "Arrival airport code must not be blank")
    private String arrivalAirportCode;

    public FlightDTO(Long id, String flightCode, Timestamp date, String ticketPrice, Integer seatsAvailable, String flightDuration, String codAirport, String codAirport1) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public String getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(String flightDuration) {
        this.flightDuration = flightDuration;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    @Override
    public String toString() {
        return "FlightDTO{" +
                "id=" + id +
                ", flightCode='" + flightCode + '\'' +
                ", date=" + date +
                ", ticketPrice='" + ticketPrice + '\'' +
                ", seatsAvailable=" + seatsAvailable +
                ", flightDuration='" + flightDuration + '\'' +
                ", departureAirportCode='" + departureAirportCode + '\'' +
                ", arrivalAirportCode='" + arrivalAirportCode + '\'' +
                '}';
    }
}