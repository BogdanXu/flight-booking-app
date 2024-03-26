package com.admin.mapper;

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
        operatorDTO.setIBAN(operator.getIBAN());
        operatorDTO.setURI(operator.getURI());

        return operatorDTO;
    }

    public static Operator toEntity(OperatorDTO operatorDTO) {
        if (operatorDTO == null) {
            return null;
        }

        Operator operator = new Operator();
        operator.setId(operatorDTO.getId());
        operator.setName(operatorDTO.getName());
        operator.setIBAN(operatorDTO.getIBAN());
        operator.setURI(operatorDTO.getURI());

        return operator;
    }
}

