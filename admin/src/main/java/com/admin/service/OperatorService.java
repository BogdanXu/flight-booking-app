package com.admin.service;

import com.admin.dto.OperatorBaseDTO;
import com.admin.dto.OperatorDTO;
import com.admin.mapper.OperatorMapper;
import com.admin.model.Operator;
import com.admin.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OperatorService {

    @Autowired
    private OperatorRepository operatorRepository;

    public Operator createOperator(OperatorDTO operatorDTO) {
        Operator operator = OperatorMapper.toEntity(operatorDTO);
        return operatorRepository.save(operator);
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
            operator.setIBAN(operatorDTO.getIBAN());
            operator.setURI(operatorDTO.getURI());
            return OperatorMapper.toDTO(operatorRepository.save(operator));
        } else {
            return null;
        }
    }

    public List<OperatorBaseDTO> sendAllOperatorURIs() {
        List<Operator> operators = operatorRepository.findAll();
        return operators.stream()
                .map(OperatorMapper::toBaseDTO)
                .collect(Collectors.toList());
    }
}
