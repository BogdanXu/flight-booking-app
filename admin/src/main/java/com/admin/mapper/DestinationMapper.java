package com.admin.mapper;

import com.admin.dto.DestinationDTO;
import com.admin.model.Destination;
import org.springframework.stereotype.Component;

@Component
public class DestinationMapper {

    public static DestinationDTO toDTO(Destination destination) {
        if (destination == null) {
            return null;
        }

        DestinationDTO destinationDTO = new DestinationDTO();
        destinationDTO.setId(destination.getId());
        destinationDTO.setCodAirport(destination.getCodAirport());
        destinationDTO.setCountry(destination.getCountry());
        destinationDTO.setCity(destination.getCity());

        return destinationDTO;
    }

    public static Destination toEntity(DestinationDTO destinationDTO) {
        if (destinationDTO == null) {
            return null;
        }

        Destination destination = new Destination();
        destination.setId(destinationDTO.getId());
        destination.setCodAirport(destinationDTO.getCodAirport());
        destination.setCountry(destinationDTO.getCountry());
        destination.setCity(destinationDTO.getCity());

        return destination;
    }
}
