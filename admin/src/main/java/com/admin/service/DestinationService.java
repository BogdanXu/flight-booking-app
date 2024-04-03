package com.admin.service;
import com.admin.dto.DestinationDTO;
import com.admin.mapper.DestinationMapper;
import com.admin.model.Destination;
import com.admin.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DestinationService {


    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public Destination createDestination(DestinationDTO destinationDTO) {
        Destination destination = DestinationMapper.toEntity(destinationDTO);
        return destinationRepository.save(destination);
    }

    public Destination findDestinationByAirportCode(String airportCode) {
        return destinationRepository.findByCodAirport(airportCode);
    }

    public List<DestinationDTO> getAllDestinations() {
        List<Destination> destinations = destinationRepository.findAll();
        return destinations.stream()
                .map(DestinationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }

    public DestinationDTO updateDestination(Long id, DestinationDTO destinationDTO) {
        Optional<Destination> optionalDestination = destinationRepository.findById(id);
        if (optionalDestination.isPresent()) {
            Destination destination = optionalDestination.get();
            destination.setCodAirport(destinationDTO.getCodAirport());
            destination.setCountry(destinationDTO.getCountry());
            destination.setCity(destinationDTO.getCity());
            return DestinationMapper.toDTO(destinationRepository.save(destination));
        } else {
            return null;
        }
    }
}

