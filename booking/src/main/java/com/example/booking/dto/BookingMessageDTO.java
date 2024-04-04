package com.example.booking.dto;

public class BookingMessageDTO {
    private Long bookingId;

    private Long flightId;
    private Integer numberOfSeats;

    private Boolean available;

    public BookingMessageDTO() {
    }

    public BookingMessageDTO(Long bookingId, Long flightId, Integer numberOfSeats) {
        this.bookingId = bookingId;
        this.flightId = flightId;
        this.numberOfSeats = numberOfSeats;
    }

    public BookingMessageDTO(Long bookingId, Boolean available) {
        this.bookingId = bookingId;
        this.available = available;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
}