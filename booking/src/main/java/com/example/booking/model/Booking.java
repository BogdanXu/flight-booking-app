package com.example.booking.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "bookings")
public class Booking {
    @Id
    private String id;

    private Flight flight;

    private LocalDateTime bookingDate;

    private LocalDateTime expirationDate;

    private String seatNumber;

    private BookingStatus bookingStatus;

    public Booking() {
    }

    public Booking(Flight flight, LocalDateTime bookingDate, LocalDateTime expirationDate, String seatNumber, BookingStatus bookingStatus) {
        this.flight = flight;
        this.bookingDate = bookingDate;
        this.expirationDate = expirationDate;
        this.seatNumber = seatNumber;
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

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
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
                ", seatNumber='" + seatNumber + '\'' +
                ", bookingStatus=" + bookingStatus +
                '}';
    }
}