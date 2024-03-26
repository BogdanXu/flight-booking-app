package com.operator.controller;

import com.operator.dto.DestinationDTO;
import com.operator.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/destinations")
@Validated
public class DestinationController {

    private final DestinationService destinationService;

    @Autowired
    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public List<DestinationDTO> getAllDestinations() {
        return destinationService.getAllDestinations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinationDTO> getDestinationById(@PathVariable Long id) {
        DestinationDTO destinationDTO = destinationService.getDestinationById(id);
        return ResponseEntity.ok(destinationDTO);
    }

    @PostMapping
    public ResponseEntity<DestinationDTO> createDestination(@Valid @RequestBody DestinationDTO destinationDTO) {
        DestinationDTO createdDestination = destinationService.createDestination(destinationDTO);
        return new ResponseEntity<>(createdDestination, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DestinationDTO> updateDestination(@PathVariable Long id, @Valid @RequestBody DestinationDTO destinationDTO) {
        DestinationDTO updatedDestination = destinationService.updateDestination(id, destinationDTO);
        return ResponseEntity.ok(updatedDestination);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.noContent().build();
    }
}