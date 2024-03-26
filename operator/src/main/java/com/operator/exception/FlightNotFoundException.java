package com.operator.exception;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(Long id) {
        super("Flight with ID " + id + " not found.");
    }

    public FlightNotFoundException(String flightCode) {
        super("Flight with code " + flightCode + " not found.");
    }
}