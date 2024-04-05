package com.payments.payments.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Document(collection = "payments")
public class PaymentDetail {
    @Id
    private String id;
    private String bookingId;
    private Integer sum;
    private LocalDateTime paymentInitiationDate;
    private String status; // INITIATED, SUCCESS, REJECTED

    private String clientIban;
    private String operatorIban;

    private Boolean paymentValidation;

    public PaymentDetail() {
    }

    public PaymentDetail(String id, String bookingId, Integer sum, LocalDateTime paymentInitiationDate,
                         String status, String clientIban, String operatorIban, Boolean paymentValidation) {
        this.id = id;
        this.bookingId = bookingId;
        this.sum = sum;
        this.paymentInitiationDate = paymentInitiationDate;
        this.status = status;
        this.clientIban = clientIban;
        this.operatorIban = operatorIban;
        this.paymentValidation = paymentValidation;
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


    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
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

    public String getClientIban() {
        return clientIban;
    }

    public void setClientIban(String clientIban) {
        this.clientIban = clientIban;
    }

    public String getOperatorIban() {
        return operatorIban;
    }

    public void setOperatorIban(String operatorIban) {
        this.operatorIban = operatorIban;
    }

    public Boolean getPaymentValidation() {
        return paymentValidation;
    }

    public void setPaymentValidation(Boolean paymentValidation) {
        this.paymentValidation = paymentValidation;
    }

    @Override
    public String toString() {
        return "PaymentDetail{" +
                "id='" + id + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", sum=" + sum +
                ", paymentInitiationDate=" + paymentInitiationDate +
                ", status='" + status + '\'' +
                ", clientIban='" + clientIban + '\'' +
                ", operatorIban='" + operatorIban + '\'' +
                ", paymentValidation=" + paymentValidation +
                '}';
    }
}


