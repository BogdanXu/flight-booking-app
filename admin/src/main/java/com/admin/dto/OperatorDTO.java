package com.admin.dto;

public class OperatorDTO {
    private long id;
    private String name;
    private String IBAN;
    private String URI;

    public OperatorDTO() {}

    public OperatorDTO(long id, String name, String IBAN, String URI) {
        this.id = id;
        this.name = name;
        this.IBAN = IBAN;
        this.URI = URI;
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

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

}
