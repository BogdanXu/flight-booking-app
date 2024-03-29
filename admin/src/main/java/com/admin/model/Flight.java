package com.admin.model;

import jakarta.persistence.*;
import java.security.Timestamp;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "flight_code")
    private String flightCode;
    @Column(name = "date")
    private Timestamp date;
    @Column(name = "ticket_price")
    private String ticketPrice;
    @Column(name = "seats_available")
    private Integer seatsAvailable;
    @Column(name = "flight_duration")
    private String flightDuration;
    @ManyToOne
    @JoinColumn(name = "opertor_id")
    Operator operator;
    @ManyToOne
    @JoinColumn(name = "departure_airport_id")
    Destination departureAirport;
    @ManyToOne
    @JoinColumn(name = "arrival_airport_id")
    Destination arrivalAirport;

    public Flight() {
    }

    public Flight(Long id, String flightCode, Timestamp date, String ticketPrice, Integer seatsAvailable, String flightDuration,
                  Operator operator, Destination departureAirport, Destination arrivalAirport) {
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

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
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
                ", operator=" + operator +
                ", departureAirport=" + departureAirport +
                ", arrivalAirport=" + arrivalAirport +
                '}';
    }
}
