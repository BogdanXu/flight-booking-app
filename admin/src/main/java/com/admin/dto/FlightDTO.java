package com.admin.dto;

import java.security.Timestamp;

public class FlightDTO {
    private Long id;
    private String flightCode;
    private Timestamp date;
    private String ticketPrice;
    private Integer seatsAvailable;
    private String flightDuration;
    private OperatorDTO operator;
    private DestinationDTO departureAirport;
    private DestinationDTO arrivalAirport;

    public FlightDTO() {}

    public FlightDTO(Long id, String flightCode, Timestamp date, String ticketPrice, Integer seatsAvailable,
                     String flightDuration, OperatorDTO operator, DestinationDTO departureAirport, DestinationDTO arrivalAirport) {
        this.id = id;
        this.flightCode = flightCode;
        this.date = date;
        this.ticketPrice = ticketPrice;
        this.seatsAvailable = seatsAvailable;
        this.flightDuration = flightDuration;
        this.operator = operator;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
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

    public OperatorDTO getOperator() {
        return operator;
    }

    public void setOperator(OperatorDTO operator) {
        this.operator = operator;
    }

    public DestinationDTO getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(DestinationDTO departureAirport) {
        this.departureAirport = departureAirport;
    }

    public DestinationDTO getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(DestinationDTO arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }
}

