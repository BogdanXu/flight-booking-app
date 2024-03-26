package com.operator.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class DestinationDTO {
    private Long id;

    @NotBlank(message = "Airport code must not be blank")
    @Size(max = 10, message = "Airport code must be at most 10 characters")
    private String codAirport;

    @NotBlank(message = "Country must not be blank")
    private String country;

    @NotBlank(message = "City must not be blank")
    private String city;
    public DestinationDTO() {
    }

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

    @Override
    public String toString() {
        return "DestinationDTO{" +
                "id=" + id +
                ", codAirport='" + codAirport + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}