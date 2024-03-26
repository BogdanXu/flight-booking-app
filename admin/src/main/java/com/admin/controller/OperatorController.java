package com.admin.controller;

import com.admin.dto.OperatorBaseDTO;
import com.admin.dto.OperatorDTO;
import com.admin.mapper.OperatorMapper;
import com.admin.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operator")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;

    @PostMapping
    public ResponseEntity<OperatorDTO> createOperator(@RequestBody OperatorDTO operatorDTO) {
        OperatorDTO createdOperator = OperatorMapper.toDTO(operatorService.createOperator(operatorDTO));
        return new ResponseEntity<>(createdOperator, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OperatorDTO>> getAllOperators() {
        List<OperatorDTO> operatorDTOList = operatorService.getAllOperators();
        return new ResponseEntity<>(operatorDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperator(@PathVariable Long id) {
        operatorService.deleteOperator(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperatorDTO> updateOperator(@PathVariable Long id, @RequestBody OperatorDTO operatorDTO) {
        OperatorDTO updatedOperator = operatorService.updateOperator(id, operatorDTO);
        if (updatedOperator != null) {
            return new ResponseEntity<>(updatedOperator, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/uris")
    public ResponseEntity<List<OperatorBaseDTO>> sendAllOperatorURIs() {
        List<OperatorBaseDTO> operatorBaseDTOList = operatorService.sendAllOperatorURIs();
        return new ResponseEntity<>(operatorBaseDTOList, HttpStatus.OK);
    }
}
