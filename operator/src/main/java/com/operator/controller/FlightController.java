package com.operator.controller;

import com.operator.dto.FlightDTO;
import com.operator.model.Flight;
import com.operator.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;


    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

//    @GetMapping
//    public List<FlightDTO> getAllFlights() {
//        return flightService.getAllFlights();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<FlightDTO> getFlightById(@PathVariable Long id) {
//        FlightDTO flightDTO = flightService.getFlightById(id);
//        return ResponseEntity.ok(flightDTO);
//    }
//
//
//
//    @PostMapping
//    public ResponseEntity<FlightDTO> createFlight(@Valid @RequestBody FlightDTO flightDTO) {
//        FlightDTO createdFlight = flightService.createFlight(flightDTO);
//        return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<FlightDTO> updateFlight(@PathVariable Long id, @Valid @RequestBody FlightDTO flightDTO) {
//        FlightDTO updatedFlight = flightService.updateFlight(id, flightDTO);
//        return ResponseEntity.ok(updatedFlight);
//    }
@GetMapping
public Flux<FlightDTO> getAllFlights() {
    return flightService.getAllFlights();
}

    @GetMapping("/{id}")
    public Mono<ResponseEntity<FlightDTO>> getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<FlightDTO>> createFlight(@Valid @RequestBody FlightDTO flightDTO) {
        return flightService.createFlight(flightDTO)
                .map(createdFlight -> ResponseEntity.status(HttpStatus.CREATED).body(createdFlight));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<FlightDTO>> updateFlight(@PathVariable Long id, @Valid @RequestBody FlightDTO flightDTO) {
        return flightService.updateFlight(id, flightDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Flux<FlightDTO>> searchFlights(
            @RequestParam(required = false) String startDestination,
            @RequestParam(required = false) String endDestination,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Flux<FlightDTO> foundFlights = flightService.searchFlights(startDestination, endDestination, startDate, endDate);
        return ResponseEntity.ok(foundFlights);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
}