package com.example.booking.model;

public class PaymentUpdate {
    private String bookingId;
    private PaymentStatus paymentStatus;
    private BookingStatus bookingStatus;

    public PaymentUpdate(String bookingId, PaymentStatus paymentStatus, BookingStatus bookingStatus) {
        this.bookingId = bookingId;
        this.paymentStatus = paymentStatus;
        this.bookingStatus = bookingStatus;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
