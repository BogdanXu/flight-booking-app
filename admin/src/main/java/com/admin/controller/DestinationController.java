package com.admin.controller;

import com.admin.dto.DestinationDTO;
import com.admin.mapper.DestinationMapper;
import com.admin.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destination")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @PostMapping
    public ResponseEntity<DestinationDTO> createDestination(@RequestBody DestinationDTO destinationDTO) {
        DestinationDTO createdDestination = DestinationMapper.toDTO(destinationService.createDestination(destinationDTO));
        return new ResponseEntity<>(createdDestination, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DestinationDTO>> getAllDestinations() {
        List<DestinationDTO> destinationDTOList = destinationService.getAllDestinations();
        return new ResponseEntity<>(destinationDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DestinationDTO> updateDestination(@PathVariable Long id, @RequestBody DestinationDTO destinationDTO) {
        DestinationDTO updatedDestination = destinationService.updateDestination(id, destinationDTO);
        if (updatedDestination != null) {
            return new ResponseEntity<>(updatedDestination, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

