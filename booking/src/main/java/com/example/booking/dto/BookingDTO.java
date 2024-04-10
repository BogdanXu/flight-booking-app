package com.example.booking.dto;

import com.example.booking.model.BookingStatus;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

public class BookingDTO {
    private FlightDTO flight;

    private LocalDateTime bookingDate;

    private LocalDateTime expirationDate;

    @NotEmpty(message = "Seats list must not be empty")
    private List<String> seats;

    private BookingStatus bookingStatus;

    public FlightDTO getFlight() {
        return flight;
    }

    public void setFlight(FlightDTO flight) {
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
}