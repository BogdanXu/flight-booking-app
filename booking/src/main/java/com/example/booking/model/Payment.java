package com.example.booking.model;

public class Payment {
    private String bookingId;
    private String clientIban;
    private String operatorIban;
    private Integer sum;

    public Payment(String bookingId, String clientIban, String operatorIban, Integer sum) {
        this.bookingId = bookingId;
        this.clientIban = clientIban;
        this.operatorIban = operatorIban;
        this.sum = sum;
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

    public String getOperatorIban() {
        return operatorIban;
    }

    public void setOperatorIban(String operatorIban) {
        this.operatorIban = operatorIban;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "bookingId='" + bookingId + '\'' +
                ", clientIban='" + clientIban + '\'' +
                ", operatorIban='" + operatorIban + '\'' +
                ", sum=" + sum +
                '}';
    }
}
