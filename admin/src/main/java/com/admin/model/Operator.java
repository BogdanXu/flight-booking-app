package com.admin.model;

import jakarta.persistence.*;

@Entity
public class Operator {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "iban")
    private String IBAN;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public Operator(long id, String name, String IBAN) {
        this.id = id;
        this.name = name;
        this.IBAN = IBAN;
    }

    public Operator() {

    }

    @Override
    public String toString() {
        return "Operator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", IBAN='" + IBAN + '\'' +
                '}';
    }
}
