package com.payments.payments.service;

import com.payments.payments.dto.PaymentDetailConfirmationDTO;
import com.payments.payments.model.PaymentDetail;
import com.payments.payments.repository.PaymentDetailRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PaymentDetailService {


    private final KafkaTemplate<String, PaymentDetail> kafkaTemplateConsumer;
    private final KafkaTemplate<String, PaymentDetailConfirmationDTO> kafkaTemplateProducer;

    private final PaymentDetailRepository paymentDetailRepository;



    public PaymentDetailService(KafkaTemplate<String, PaymentDetail> kafkaTemplateConsumer, KafkaTemplate<String,
            PaymentDetailConfirmationDTO> kafkaTemplateProducer, PaymentDetailRepository bookingRepository) {
        this.kafkaTemplateConsumer = kafkaTemplateConsumer;
        this.kafkaTemplateProducer = kafkaTemplateProducer;
        this.paymentDetailRepository = bookingRepository;
    }

    public Mono<PaymentDetail> savePaymentDetail(PaymentDetail paymentDetail) {
        return paymentDetailRepository.save(paymentDetail);
    }


}
