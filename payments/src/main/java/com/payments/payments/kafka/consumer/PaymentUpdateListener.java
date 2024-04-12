package com.payments.payments.kafka.consumer;

import com.payments.payments.dto.PaymentDetailConfirmationDTO;
import com.payments.payments.dto.PaymentDetailDTO;
import com.payments.payments.mapper.PaymentDetailMapper;
import com.payments.payments.model.PaymentDetail;
import com.payments.payments.repository.PaymentDetailRepository;
import com.payments.payments.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class PaymentUpdateListener {

    private static final Logger log = LoggerFactory.getLogger(PaymentUpdateListener.class);
    private final PaymentDetailRepository paymentDetailRepository;

    private final KafkaTemplate<String, PaymentDetailConfirmationDTO> kafkaTemplate;

    private final PaymentService paymentService;

    public PaymentUpdateListener(PaymentDetailRepository paymentDetailRepository, KafkaTemplate<String,
            PaymentDetailConfirmationDTO> kafkaTemplate, PaymentService paymentService) {
        this.paymentDetailRepository = paymentDetailRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "payment-request")
    public void listen(PaymentDetailDTO paymentDetailDTO) {


        PaymentDetail paymentDetail = PaymentDetailMapper.fromDTO(paymentDetailDTO);
        paymentDetailRepository.save(paymentDetail).subscribe();

        log.info("paymentDetail obj: " + paymentDetail.toString());

        boolean confirmPayment = paymentService.verifyPayment(paymentDetail.getBookingId()).block();

        log.info("confirmPayment boolean : " + confirmPayment);

        if (confirmPayment) {
            paymentDetail.setStatus("ACCEPTED");
            PaymentDetailConfirmationDTO paymentDetailConfirmationDTO = new PaymentDetailConfirmationDTO(paymentDetail.getBookingId(), true);
            paymentDetailRepository.save(paymentDetail).subscribe();

            kafkaTemplate.send("payment-request-confirmation", paymentDetailConfirmationDTO);
        } else {
            paymentDetail.setStatus("REJECTED");
            PaymentDetailConfirmationDTO paymentDetailConfirmationDTO = new PaymentDetailConfirmationDTO(paymentDetail.getBookingId(), false);
            paymentDetailRepository.save(paymentDetail).subscribe();

            kafkaTemplate.send("payment-request-confirmation", paymentDetailConfirmationDTO);
        }

    }

}