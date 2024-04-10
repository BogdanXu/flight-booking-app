package com.example.booking.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Document(collection = "bookings")
public class Booking {
    @Id
    private String id;

    private Flight flight;

    private LocalDateTime bookingDate;

    private LocalDateTime expirationDate;

    private List<String> seats;

    private BookingStatus bookingStatus;

    public Booking() {
    }

    public Booking(Flight flight, LocalDateTime bookingDate, LocalDateTime expirationDate, List<String> seats, BookingStatus bookingStatus) {
        this.flight = flight;
        this.bookingDate = bookingDate;
        this.expirationDate = expirationDate;
        this.seats = seats;
        this.bookingStatus = bookingStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<String> getSeats() {
        return seats;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", flight=" + flight +
                ", bookingDate=" + bookingDate +
                ", expirationDate=" + expirationDate +
                ", seatNumber=" + seats +
                ", bookingStatus=" + bookingStatus +
                '}';
    }

}