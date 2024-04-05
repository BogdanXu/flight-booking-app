package com.example.booking.model;

import java.time.LocalDateTime;

public class Flight {
    private Long id;
    private String flightCode;
    private LocalDateTime flightDate;
    private String ticketPrice;
    private Integer seatsAvailable;
    private String flightDuration;
    private String departureAirportCode;
    private String arrivalAirportCode;

    public Flight() {
    }

    public Flight(Long id, String flightCode, LocalDateTime flightDate, String ticketPrice, Integer seatsAvailable, String flightDuration, String departureAirportCode, String arrivalAirportCode) {
        this.id = id;
        this.flightCode = flightCode;
        this.flightDate = flightDate;
        this.ticketPrice = ticketPrice;
        this.seatsAvailable = seatsAvailable;
        this.flightDuration = flightDuration;
        this.departureAirportCode = departureAirportCode;
        this.arrivalAirportCode = arrivalAirportCode;
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

    public LocalDateTime getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(LocalDateTime flightDate) {
        this.flightDate = flightDate;
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
        return "Flight{" +
                "id=" + id +
                ", flightCode='" + flightCode + '\'' +
                ", flightDate=" + flightDate +
                ", ticketPrice='" + ticketPrice + '\'' +
                ", seatsAvailable=" + seatsAvailable +
                ", flightDuration='" + flightDuration + '\'' +
                ", departureAirportCode='" + departureAirportCode + '\'' +
                ", arrivalAirportCode='" + arrivalAirportCode + '\'' +
                '}';
    }
}