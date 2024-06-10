package com.payments.payments.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Document(collection = "payments")
@Component
public class PaymentDetail {
    @Id
    private String id;
    private String bookingId;
    private String clientIban;
    private String operatorIban;
    private double amount;
    private LocalDateTime paymentInitiationDate;
    private String status; // INITIATED, SUCCESS, REJECTED

    public PaymentDetail() {
    }

    public PaymentDetail(String id, String bookingId, String clientIban, String operatorIban, double amount, LocalDateTime paymentInitiationDate, String status) {
        this.id = id;
        this.bookingId = bookingId;
        this.clientIban = clientIban;
        this.operatorIban = operatorIban;
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

    public String getClientIban() {
        return clientIban;
    }

    public void setClientIban(String clientIban) {
        this.clientIban = clientIban;
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

    public String getOperatorIban() {
        return operatorIban;
    }

    public void setOperatorIban(String operatorIban) {
        this.operatorIban = operatorIban;
    }

    @Override
    public String toString() {
        return "PaymentDetail{" +
                "id='" + id + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", clientIban='" + clientIban + '\'' +
                ", operatorIban='" + operatorIban + '\'' +
                ", amount=" + amount +
                ", paymentInitiationDate=" + paymentInitiationDate +
                ", status='" + status + '\'' +
                '}';
    }
}
