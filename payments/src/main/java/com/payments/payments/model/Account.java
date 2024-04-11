package com.payments.payments.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class Account {

    private String iban;
    private Double balance;

    public Account() {
    }

    public Account(String iban, Double balance) {
        this.iban = iban;
        this.balance = balance;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
