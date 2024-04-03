package com.search.controller;

import com.search.dto.FlightDTO;
import com.search.service.FlightSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/search-flights")
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    public FlightSearchController(FlightSearchService flightSearchService) {
        this.flightSearchService = flightSearchService;
    }

    @GetMapping
    public Flux<FlightDTO> getFlights(@RequestParam String departure,
                                      @RequestParam String destination,
                                      @RequestParam LocalDate date) {
        return flightSearchService.searchFlights(departure, destination, date);
    }
}
