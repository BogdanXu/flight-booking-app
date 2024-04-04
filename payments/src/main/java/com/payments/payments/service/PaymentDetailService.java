package com.payments.payments.service;

import com.payments.payments.model.PaymentDetail;
import com.payments.payments.repository.PaymentDetailRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PaymentDetailService {

    private final PaymentDetailRepository paymentDetailRepository;


    public PaymentDetailService(PaymentDetailRepository bookingRepository) {
        this.paymentDetailRepository = bookingRepository;
    }

    public Mono<PaymentDetail> savePaymentDetail(PaymentDetail paymentDetail) {
        return paymentDetailRepository.save(paymentDetail);
    }


}
