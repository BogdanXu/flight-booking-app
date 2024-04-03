package com.admin.service;

import com.admin.dto.FlightDTO;
import com.admin.dto.OperatorBaseDTO;
import com.admin.dto.OperatorDTO;
import com.admin.mapper.OperatorMapper;
import com.admin.model.Flight;
import com.admin.model.Operator;
import com.admin.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OperatorService {

    private final OperatorRepository operatorRepository;

    private final FlightService flightService;

    public OperatorService(OperatorRepository operatorRepository, FlightService flightService) {
        this.operatorRepository = operatorRepository;
        this.flightService = flightService;
    }


    public Operator createOperator(OperatorDTO operatorDTO) {
        Operator operator = OperatorMapper.toEntity(operatorDTO);
        return operatorRepository.save(operator);
    }

    public OperatorBaseDTO findOperatorById(Long id) {
        if (operatorRepository.findById(id).isPresent())
            return OperatorMapper.toBaseDTO(operatorRepository.findById(id).get());
        else
            return null;
    }

    public List<OperatorDTO> getAllOperators() {
        List<Operator> operators = operatorRepository.findAll();
        return operators.stream()
                .map(OperatorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteOperator(Long id) {
        operatorRepository.deleteById(id);
    }

    public OperatorDTO updateOperator(Long id, OperatorDTO operatorDTO) {
        Optional<Operator> optionalOperator = operatorRepository.findById(id);
        if (optionalOperator.isPresent()) {
            Operator operator = optionalOperator.get();
            operator.setName(operatorDTO.getName());
            operator.setIban(operatorDTO.getIban());
            operator.setUri(operatorDTO.getUri());
            return OperatorMapper.toDTO(operatorRepository.save(operator));
        } else {
            return null;
        }
    }

    public List<OperatorBaseDTO> sendAllOperatorURIs(String departure, String arrival) {
        List<FlightDTO> flightsList = flightService.getAllFlightsByDepartureAndArrival(departure, arrival);
        List<OperatorBaseDTO> operators = new ArrayList<>();
        for (FlightDTO flight : flightsList) {
            if (operatorRepository.findById(flight.getOperatorId()).isPresent())
                operators.add(OperatorMapper.toBaseDTO(operatorRepository.findById(flight.getOperatorId()).get()));
        }
        return operators;
    }
}
