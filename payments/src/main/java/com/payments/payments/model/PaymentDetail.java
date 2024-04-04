package com.payments.payments.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Document(collection = "payments")
public class PaymentDetail {
    @Id
    private String id;
    private String bookingId;
    private String iban;
    private double amount;
    private LocalDateTime paymentInitiationDate;
    private String status; // INITIATED, SUCCESS, REJECTED

    public PaymentDetail() {
    }

    public PaymentDetail(String id, String bookingId, String iban, double amount, LocalDateTime paymentInitiationDate, String status) {
        this.id = id;
        this.bookingId = bookingId;
        this.iban = iban;
        this.amount = amount;
        this.paymentInitiationDate = paymentInitiationDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentInitiationDate() {
        return paymentInitiationDate;
    }

    public void setPaymentInitiationDate(LocalDateTime paymentInitiationDate) {
        this.paymentInitiationDate = paymentInitiationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
