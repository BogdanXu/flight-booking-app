package com.operator.exception;

public class DestinationNotFoundException extends RuntimeException {
    public DestinationNotFoundException(Long id) {
        super("Destination with ID " + id + " not found.");
    }

    public DestinationNotFoundException(String codAirport) {
        super("Destination with airport code " + codAirport + " not found.");
    }
}