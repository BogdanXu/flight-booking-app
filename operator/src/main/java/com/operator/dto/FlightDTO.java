package com.operator.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class FlightDTO {
    private Long id;

    @NotBlank(message = "Flight code must not be blank")
    private String flightCode;

    private LocalDateTime date;

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

    public FlightDTO(Long id, String flightCode, LocalDateTime date, String ticketPrice, Integer seatsAvailable, String flightDuration, String departureAirportCode, String arrivalAirportCode) {
        this.id = id;
        this.flightCode = flightCode;
        this.date = date;
        this.ticketPrice = ticketPrice;
        this.seatsAvailable = seatsAvailable;
        this.flightDuration = flightDuration;
        this.departureAirportCode = departureAirportCode;
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public Long getId() {
        return id;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    public String getFlightDuration() {
        return flightDuration;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public void setFlightDuration(String flightDuration) {
        this.flightDuration = flightDuration;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
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