package com.search.service;

import com.search.dto.FlightDTO;
import com.search.dto.OperatorBaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.List;

@Service
public class FlightSearchService {

    private final WebClient adminClient = WebClient.create("http://localhost:8080");

    //apelez serviciul de admin si colectez lista de operatori pentru cautarea facuta
    public Flux<OperatorBaseDTO> operatorsList(String departure, String arrival, Date date){
        return adminClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/uris")
                        .queryParam("departure", departure)
                        .queryParam("destination", arrival)
                        .queryParam("date", date)
                        .build())
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
    private Flux<FlightDTO> callOperatorAndRetrieveFlights(String operatorUri, String departure, String arrival, Date date) {
        WebClient operatorClient = WebClient.create(operatorUri);
        return operatorClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flights")
                        .queryParam("departure", departure)
                        .queryParam("destination", arrival)
                        .queryParam("date", date)
                        .build())
                .retrieve()
                .bodyToFlux(FlightDTO.class);
    }

    //initializez cautarea de zboruri folosind ulterior si filtre de cautare
    public Flux<FlightDTO> searchFlights(String departure, String arrival, Date date) {
        Flux<OperatorBaseDTO> operatorBaseDTOFlux = operatorsList(departure, arrival, date);
        return extractDistinctOperatorUris(operatorBaseDTOFlux)
                .flatMap(operatorUri -> callOperatorAndRetrieveFlights(operatorUri, departure, arrival, date));
    }

}
