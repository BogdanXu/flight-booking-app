package com.operator.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "flight")
public class Flight {
    @Id
    private Long id;
    private String flightCode;
    private LocalDateTime flightDate;
    private String ticketPrice;
    private Integer seatsAvailable;
    private String flightDuration;

    private Destination departureAirport;
    private Destination arrivalAirport;

    public Flight() {
    }

    public Flight(Long id, String flightCode, LocalDateTime flightDate, String ticketPrice, Integer seatsAvailable, String flightDuration, Destination departureAirport, Destination arrivalAirport) {
        this.id = id;
        this.flightCode = flightCode;
        this.flightDate = flightDate;
        this.ticketPrice = ticketPrice;
        this.seatsAvailable = seatsAvailable;
        this.flightDuration = flightDuration;
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

    public Destination getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Destination departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Destination getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Destination arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
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
                ", departureAirport=" + departureAirport +
                ", arrivalAirport=" + arrivalAirport +
                '}';
    }
}
