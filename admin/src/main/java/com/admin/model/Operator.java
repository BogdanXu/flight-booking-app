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
    private String iban;

    @Column(name="uri")
    private String uri;

    public Operator() {
    }

    public Operator(long id, String name, String iban, String uri) {
        this.id = id;
        this.name = name;
        this.iban = iban;
        this.uri = uri;
    }

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

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", IBAN='" + iban + '\'' +
                ", URI='" + uri + '\'' +
                '}';
    }
}
