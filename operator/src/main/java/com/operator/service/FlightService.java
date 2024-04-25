package com.operator.service;

import com.operator.dto.FlightDTO;
import com.operator.exception.DestinationNotFoundException;
import com.operator.exception.FlightNotFoundException;
import com.operator.model.Destination;
import com.operator.model.Flight;
import com.operator.repository.DestinationRepository;
import com.operator.repository.FlightRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FlightService {

    @Value("${airline.iata.code}")
    private String IATACode;
    private final FlightRepository flightRepository;
    private final DestinationRepository destinationRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository, DestinationRepository destinationRepository) {
        this.flightRepository = flightRepository;
        this.destinationRepository = destinationRepository;
    }

//    public List<FlightDTO> getAllFlights() {
//        List<Flight> flights = flightRepository.findAll();
//        return flights.stream()
//                .filter(flight -> flight.getFlightCode().startsWith(IATACode))
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    public FlightDTO getFlightById(Long id) {
//        Optional<Flight> flightOptional = flightRepository.findById(id);
//        if (flightOptional.isPresent()) {
//            return convertToDTO(flightOptional.get());
//        }
//        throw new FlightNotFoundException(id);
//    }
//
//    @Transactional
//    public FlightDTO createFlight(FlightDTO flightDTO) {
//        validateAirportCodes(flightDTO);
//        Flight flight = convertToEntity(flightDTO);
//        Flight savedFlight = flightRepository.save(flight);
//        return convertToDTO(savedFlight);
//    }
//
//    public FlightDTO updateFlight(Long id, FlightDTO flightDTO) {
//        Optional<Flight> flightOptional = flightRepository.findById(id);
//        if (flightOptional.isPresent()) {
//            Flight existingFlight = flightOptional.get();
//
//            if (flightDTO.getFlightCode() != null) {
//                existingFlight.setFlightCode(flightDTO.getFlightCode());
//            }
//            if (flightDTO.getDate() != null) {
//                existingFlight.setFlightDate(flightDTO.getDate());
//            }
//            if (flightDTO.getTicketPrice() != null) {
//                existingFlight.setTicketPrice(flightDTO.getTicketPrice());
//            }
//            if (flightDTO.getSeatsAvailable() != null) {
//                existingFlight.setSeatsAvailable(flightDTO.getSeatsAvailable());
//            }
//            if (flightDTO.getFlightDuration() != null) {
//                existingFlight.setFlightDuration(flightDTO.getFlightDuration());
//            }
//            if (flightDTO.getDepartureAirportCode() != null) {
//                Destination departureDestination = destinationRepository.findByCodAirport(flightDTO.getDepartureAirportCode())
//                        .orElseThrow(() -> new DestinationNotFoundException("Destination with code " + flightDTO.getDepartureAirportCode() + " not found."));
//                existingFlight.setDepartureAirport(departureDestination);
//            }
//            if (flightDTO.getArrivalAirportCode() != null) {
//                Destination arrivalDestination = destinationRepository.findByCodAirport(flightDTO.getArrivalAirportCode())
//                        .orElseThrow(() -> new DestinationNotFoundException("Destination with code " + flightDTO.getArrivalAirportCode() + " not found."));
//                existingFlight.setArrivalAirport(arrivalDestination);
//            }
//            Flight updatedFlight = flightRepository.save(existingFlight);
//            return convertToDTO(updatedFlight);
//        }
//        throw new FlightNotFoundException(id);
//    }
//
//    public void deleteFlight(Long id) {
//        if (!flightRepository.existsById(id)) {
//            throw new FlightNotFoundException(id);
//        }
//        flightRepository.deleteById(id);
//    }

    public Flux<FlightDTO> getAllFlights() {
        return flightRepository.findAll()
                .filter(flight -> flight.getFlightCode().startsWith(IATACode))
                .map(this::convertToDTO);
    }

    public Mono<FlightDTO> getFlightById(Long id) {
        return flightRepository.findById(id)
                .map(this::convertToDTO)
                .switchIfEmpty(Mono.error(new FlightNotFoundException(id)));
    }

    public Mono<FlightDTO> createFlight(FlightDTO flightDTO) {
        validateAirportCodes(flightDTO);
        Flight flight = convertToEntity(flightDTO);
        return flightRepository.save(flight)
                .map(this::convertToDTO);
    }

    public Mono<FlightDTO> updateFlight(Long id, FlightDTO flightDTO) {
        return flightRepository.findById(id)
                .flatMap(existingFlight -> {
                    // Update existingFlight fields
                    return flightRepository.save(existingFlight);
                })
                .map(this::convertToDTO)
                .switchIfEmpty(Mono.error(new FlightNotFoundException(id)));
    }

    public Mono<Void> deleteFlight(Long id) {
        return flightRepository.existsById(id)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new FlightNotFoundException(id));
                    } else {
                        return flightRepository.deleteById(id).then();
                    }
                });
    }

    private FlightDTO convertToDTO(Flight flight) {
        return new FlightDTO(
                flight.getId(),
                flight.getFlightCode(),
                flight.getFlightDate(),
                flight.getTicketPrice(),
                flight.getSeatsAvailable(),
                flight.getFlightDuration(),
                flight.getDepartureAirport().getCodAirport(),
                flight.getArrivalAirport().getCodAirport()
        );
    }

    private Flight convertToEntity(FlightDTO flightDTO) {
        Destination departureDestination = destinationRepository.findByCodAirport(flightDTO.getDepartureAirportCode())
                .orElseThrow(() -> new DestinationNotFoundException("Destination with code " + flightDTO.getDepartureAirportCode() + " not found."));

        Destination arrivalDestination = destinationRepository.findByCodAirport(flightDTO.getArrivalAirportCode())
                .orElseThrow(() -> new DestinationNotFoundException("Destination with code " + flightDTO.getArrivalAirportCode() + " not found."));

        return new Flight(
                flightDTO.getId(),
                flightDTO.getFlightCode(),
                flightDTO.getDate(),
                flightDTO.getTicketPrice(),
                flightDTO.getSeatsAvailable(),
                flightDTO.getFlightDuration(),
                departureDestination,
                arrivalDestination
        );
    }


    private void validateAirportCodes(FlightDTO flightDTO) {
        String departureAirportCode = flightDTO.getDepartureAirportCode();
        String arrivalAirportCode = flightDTO.getArrivalAirportCode();

        if (!destinationRepository.existsByCodAirport(departureAirportCode)) {
            throw new DestinationNotFoundException("Destination with code " + departureAirportCode + " not found.");
        }

        if (!destinationRepository.existsByCodAirport(arrivalAirportCode)) {
            throw new DestinationNotFoundException("Destination with code " + arrivalAirportCode + " not found.");
        }
    }

//    public List<FlightDTO> searchFlights(String startDestination, String endDestination, LocalDate startDate, LocalDate endDate) {
////        return flightRepository.findAll((root, query, criteriaBuilder) -> {
////            List<Predicate> predicates = new ArrayList<>();
////
////            if (startDestination != null) {
////                predicates.add(criteriaBuilder.equal(root.get("departureAirport").get("codAirport"), startDestination));
////            }
////
////            if (endDestination != null) {
////                predicates.add(criteriaBuilder.equal(root.get("arrivalAirport").get("codAirport"), endDestination));
////            }
////
////            if (startDate != null && endDate != null) {
////                predicates.add(criteriaBuilder.between(root.get("flightDate"), startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay()));
////            }
////
////            if (IATACode != null && !IATACode.isEmpty()) {
////                predicates.add(criteriaBuilder.like(root.get("flightCode"), IATACode + "%"));
////            }
////
////            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
////        }).stream().map(this::convertToDTO).collect(Collectors.toList());
//        return flightRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
//    }

//    public Flux<FlightDTO> searchFlightsAlternative(String startDestination, String endDestination, LocalDate startDate, LocalDate endDate, String IATACode) {
//        Query query = new Query();
//        Criteria criteria = new Criteria();
//
//        if (startDestination != null) {
//            criteria.and("departureAirport.codAirport").is(startDestination);
//        }
//
//        if (endDestination != null) {
//            criteria.and("arrivalAirport.codAirport").is(endDestination);
//        }
//
//        if (startDate != null && endDate != null) {
//            criteria.and("flightDate").gte(startDate.atStartOfDay(ZoneOffset.UTC)).lt(endDate.plusDays(1).atStartOfDay(ZoneOffset.UTC));
//        }
//
//        if (IATACode != null && !IATACode.isEmpty()) {
//            criteria.and("flightCode").regex("^" + IATACode);
//        }
//
//        query.addCriteria(criteria);
//
//        return flightRepository.findAll(query)
//                .stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }

    public Flux<FlightDTO> searchFlights(String startDestination, String endDestination, LocalDate startDate, LocalDate endDate) {
        return flightRepository.findByCriteria(startDestination, endDestination, startDate, endDate.plusDays(1), IATACode)
                .map(this::convertToDTO);
    }
}