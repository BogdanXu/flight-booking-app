package com.admin.repository;

import com.admin.dto.OperatorBaseDTO;
import com.admin.model.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {
}
