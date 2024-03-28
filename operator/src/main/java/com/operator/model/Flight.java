package com.operator.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "flight_code")
    private String flightCode;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "ticket_price")
    private String ticketPrice;
    @Column(name = "seats_available")
    private Integer seatsAvailable;
    @Column(name = "flight_duration")
    private String flightDuration;

    @ManyToOne
    @JoinColumn(name = "departure_airpor_id")
    Destination departureAirport;
    @ManyToOne
    @JoinColumn(name = "arrival_airport_id")
    Destination arrivalAirport;

    public Flight() {
    }

    public Flight(Long id, String flightCode, LocalDateTime date, String ticketPrice, Integer seatsAvailable, String flightDuration, Destination departureAirport, Destination arrivalAirport) {
        this.id = id;
        this.flightCode = flightCode;
        this.date = date;
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
                ", date=" + date +
                ", ticketPrice='" + ticketPrice + '\'' +
                ", seatsAvailable=" + seatsAvailable +
                ", flightDuration='" + flightDuration + '\'' +
                ", departureAirport=" + departureAirport +
                ", arrivalAirport=" + arrivalAirport +
                '}';
    }
}

