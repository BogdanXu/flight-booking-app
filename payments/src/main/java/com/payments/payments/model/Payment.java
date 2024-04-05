package com.payments.payments.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Payment {
    @Id
    private String id;
    private String clientIban;
    private String operatorIban;
    private Integer sum;
    private Integer balance;
    private Boolean paymentValidation;
    public Payment() {
    }

    public Payment(String id, String clientIban, String operatorIban, Integer sum, Integer balance, Boolean paymentValidation) {
        this.id = id;
        this.clientIban = clientIban;
        this.operatorIban = operatorIban;
        this.sum = sum;
        this.balance = balance;
        this.paymentValidation = paymentValidation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Boolean getPaymentValidation() {
        return paymentValidation;
    }

    public void setPaymentValidation(Boolean paymentValidation) {
        this.paymentValidation = paymentValidation;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", clientIban='" + clientIban + '\'' +
                ", operatorIban='" + operatorIban + '\'' +
                ", sum=" + sum +
                ", balance=" + balance +
                ", paymentValidation=" + paymentValidation +
                '}';
    }
}
