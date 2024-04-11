package com.payments.payments.repository;

import com.payments.payments.model.PaymentDetail;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface PaymentDetailRepository extends ReactiveMongoRepository<PaymentDetail, String> {

    Mono<PaymentDetail> findByBookingId(String bookingId);
}
