package com.admin.mapper;

import com.admin.dto.OperatorBaseDTO;
import com.admin.dto.OperatorDTO;
import com.admin.model.Operator;
import org.springframework.stereotype.Component;

@Component
public class OperatorMapper {

    public static OperatorDTO toDTO(Operator operator) {
        if (operator == null) {
            return null;
        }

        OperatorDTO operatorDTO = new OperatorDTO();
        operatorDTO.setId(operator.getId());
        operatorDTO.setName(operator.getName());
        operatorDTO.setIban(operator.getIban());
        operatorDTO.setUri(operator.getUri());

        return operatorDTO;
    }

    public static OperatorBaseDTO toBaseDTO(Operator operator) {
        if (operator == null) {
            return null;
        }

        OperatorBaseDTO operatorDTO = new OperatorBaseDTO();
        operatorDTO.setId(operator.getId());
        operatorDTO.setName(operator.getName());
        operatorDTO.setUri(operator.getUri());

        return operatorDTO;
    }

    public static Operator toEntity(OperatorDTO operatorDTO) {
        if (operatorDTO == null) {
            return null;
        }

        Operator operator = new Operator();
        operator.setId(operatorDTO.getId());
        operator.setName(operatorDTO.getName());
        operator.setIban(operatorDTO.getIban());
        operator.setUri(operatorDTO.getUri());

        return operator;
    }

    public static Operator toBaseEntity(OperatorBaseDTO operatorDTO) {
        if (operatorDTO == null) {
            return null;
        }

        Operator operator = new Operator();
        operator.setId(operatorDTO.getId());
        operator.setName(operatorDTO.getName());
        operator.setUri(operatorDTO.getUri());

        return operator;
    }
}

