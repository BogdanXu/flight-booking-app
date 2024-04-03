package com.admin.dto;

import java.time.LocalDateTime;

public class FlightDTO {
    private Long id;
    private String flightCode;
    private LocalDateTime date;
    private String ticketPrice;
    private Integer seatsAvailable;
    private String flightDuration;
    private Long operatorId;
    private String departureAirport;
    private String arrivalAirport;

    public FlightDTO() {}

    public FlightDTO(Long id, String flightCode, LocalDateTime date, String ticketPrice, Integer seatsAvailable, String flightDuration, Long operatorId, String departureAirport, String arrivalAirport) {
        this.id = id;
        this.flightCode = flightCode;
        this.date = date;
        this.ticketPrice = ticketPrice;
        this.seatsAvailable = seatsAvailable;
        this.flightDuration = flightDuration;
        this.operatorId = operatorId;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }
}

