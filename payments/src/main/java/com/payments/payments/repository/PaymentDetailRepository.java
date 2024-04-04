package com.payments.payments.repository;

import com.payments.payments.model.PaymentDetail;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface PaymentDetailRepository extends ReactiveMongoRepository<PaymentDetail, Integer> {
}
