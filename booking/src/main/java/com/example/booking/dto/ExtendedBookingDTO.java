package com.example.booking.dto;

public class ExtendedBookingDTO extends BookingDTO {
    private String clientIban;
    private String operatorIban;
    private Integer sum;

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
}