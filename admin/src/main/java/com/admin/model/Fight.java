package com.admin.model;

import jakarta.persistence.*;
import java.security.Timestamp;

@Entity
public class Fight {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "flight_code")
    private String flightCode;
    @Column(name = "date")
    private Timestamp date;
    @Column(name = "ticket_price")
    private String ticketPrice;
    @Column(name = "seats_available")
    private Integer seatsAvailable;
    @Column(name = "fight_duration")
    private String fightDuration;
    @ManyToOne
    @JoinColumn(name = "opertor_id")
    Operator operator;
    @ManyToOne
    @JoinColumn(name = "departure_airpor_id")
    Destination departureAirport;
    @ManyToOne
    @JoinColumn(name = "arrival_airport_id")
    Destination arrivalAirport;

    public Fight() {
    }

    public Fight(Long id, String flightCode, Timestamp date, String ticketPrice, Integer seatsAvailable, String fightDuration,
                 Operator operator, Destination departureAirport, Destination arrivalAirport) {
        this.id = id;
        this.flightCode = flightCode;
        this.date = date;
        this.ticketPrice = ticketPrice;
        this.seatsAvailable = seatsAvailable;
        this.fightDuration = fightDuration;
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

    public String getFightDuration() {
        return fightDuration;
    }

    public void setFightDuration(String fightDuration) {
        this.fightDuration = fightDuration;
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
        return "Fight{" +
                "id=" + id +
                ", flightCode='" + flightCode + '\'' +
                ", date=" + date +
                ", ticketPrice='" + ticketPrice + '\'' +
                ", seatsAvailable=" + seatsAvailable +
                ", fightDuration='" + fightDuration + '\'' +
                ", operator=" + operator +
                ", departureAirport=" + departureAirport +
                ", arrivalAirport=" + arrivalAirport +
                '}';
    }
}
