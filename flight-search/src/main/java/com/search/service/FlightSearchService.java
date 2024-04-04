package com.search.service;

import com.search.dto.FlightDTO;
import com.search.dto.OperatorBaseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.time.LocalDate;
import org.springframework.web.util.UriBuilder;
@Service
public class FlightSearchService {

    private final WebClient adminClient = WebClient.create("http://admin-be:8080");

    public Flux<OperatorBaseDTO> operatorsList(String departure, String arrival) {
        String uri = "/operator/uris?departure=" + departure + "&destination=" + arrival;
        return adminClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(OperatorBaseDTO.class);
    }

    //extrag lista de uri-uri din modelele de operatori
    private Flux<String> extractDistinctOperatorUris(Flux<OperatorBaseDTO> operators) {
        return operators
                .map(OperatorBaseDTO::getUri)
                .distinct();
    }

    //apelez operatorul pentru uri ul specificat si intorc fluxul de zboruri

    private Flux<FlightDTO> callOperatorAndRetrieveFlights(String operatorUri, String departure, String arrival, LocalDate date) {
        String uri = "?startDestination=" + departure + "&endDestination=" + arrival + "&startDate=" + date + "&endDate=" + date;
        WebClient operatorClient = WebClient.create(operatorUri);
        return operatorClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(FlightDTO.class);
    }

    //initializez cautarea de zboruri folosind ulterior si filtre de cautare
    public Flux<FlightDTO> searchFlights(String departure, String arrival, LocalDate date) {
        Flux<OperatorBaseDTO> operatorBaseDTOFlux = operatorsList(departure, arrival);
        return extractDistinctOperatorUris(operatorBaseDTOFlux)
                .flatMap(operatorUri -> callOperatorAndRetrieveFlights(operatorUri, departure, arrival, date));
    }

}
