package com.example.booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ExtendedBookingDTO extends BookingDTO {
    @NotBlank(message = "Client IBAN must not be blank")
    @JsonProperty(required = true)
    private String clientIban;

    @NotBlank(message = "Operator IBAN must not be blank")
    @JsonProperty(required = true)
    private String operatorIban;

    @NotNull(message = "Sum must not be null")
    @Positive(message = "Sum must be positive")
    @JsonProperty(required = true)
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