package com.example.booking.dto;

public class PaymentDetailConfirmationDTO {
    private String bookingId;
    private Boolean paymentValidation;

    public PaymentDetailConfirmationDTO() {
    }

    public PaymentDetailConfirmationDTO(String bookingId, Boolean paymentValidation) {
        this.bookingId = bookingId;
        this.paymentValidation = paymentValidation;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Boolean getPaymentValidation() {
        return paymentValidation;
    }

    public void setPaymentValidation(Boolean paymentValidation) {
        this.paymentValidation = paymentValidation;
    }
}