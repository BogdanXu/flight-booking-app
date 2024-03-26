package com.operator.service;

import com.operator.dto.DestinationDTO;
import com.operator.exception.DestinationNotFoundException;
import com.operator.model.Destination;
import com.operator.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    @Autowired
    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public List<DestinationDTO> getAllDestinations() {
        List<Destination> destinations = destinationRepository.findAll();
        return destinations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DestinationDTO getDestinationById(Long id) {
        Optional<Destination> destinationOptional = destinationRepository.findById(id);
        if (destinationOptional.isPresent()) {
            return convertToDTO(destinationOptional.get());
        }
        throw new DestinationNotFoundException(id);
    }

    public DestinationDTO createDestination(DestinationDTO destinationDTO) {
        Destination destination = convertToEntity(destinationDTO);
        Destination savedDestination = destinationRepository.save(destination);
        return convertToDTO(savedDestination);
    }

    public DestinationDTO updateDestination(Long id, DestinationDTO destinationDTO) {
        Optional<Destination> destinationOptional = destinationRepository.findById(id);
        if (destinationOptional.isPresent()) {
            Destination existingDestination = destinationOptional.get();
            if (destinationDTO.getCodAirport() != null) {
                existingDestination.setCodAirport(destinationDTO.getCodAirport());
            }
            if (destinationDTO.getCountry() != null) {
                existingDestination.setCountry(destinationDTO.getCountry());
            }
            if (destinationDTO.getCity() != null) {
                existingDestination.setCity(destinationDTO.getCity());
            }
            Destination updatedDestination = destinationRepository.save(existingDestination);
            return convertToDTO(updatedDestination);
        }
        throw new DestinationNotFoundException(id);
    }

    public void deleteDestination(Long id) {
        if (!destinationRepository.existsById(id)) {
            throw new DestinationNotFoundException(id);
        }
        destinationRepository.deleteById(id);
    }

    private DestinationDTO convertToDTO(Destination destination) {
        return new DestinationDTO(
                destination.getId(),
                destination.getCodAirport(),
                destination.getCountry(),
                destination.getCity()
        );
    }

    private Destination convertToEntity(DestinationDTO destinationDTO) {
        Destination destination = new Destination();
        destination.setId(destinationDTO.getId());
        destination.setCodAirport(destinationDTO.getCodAirport());
        destination.setCountry(destinationDTO.getCountry());
        destination.setCity(destinationDTO.getCity());
        return destination;
    }
}