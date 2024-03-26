package com.admin.dto;

public class DestinationDTO {
    private Long id;
    private String codAirport;
    private String country;
    private String city;

    public DestinationDTO() {}

    public DestinationDTO(Long id, String codAirport, String country, String city) {
        this.id = id;
        this.codAirport = codAirport;
        this.country = country;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodAirport() {
        return codAirport;
    }

    public void setCodAirport(String codAirport) {
        this.codAirport = codAirport;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
