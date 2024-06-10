package com.payments.payments.kafka.consumer;

import com.payments.payments.dto.PaymentDetailConfirmationDTO;
import com.payments.payments.dto.PaymentDetailDTO;
import com.payments.payments.mapper.PaymentDetailMapper;
import com.payments.payments.model.PaymentDetail;
import com.payments.payments.repository.PaymentDetailRepository;
import com.payments.payments.service.PaymentService;
import com.payments.payments.service.PaypalService;
import com.payments.payments.service.StripeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URI;

import static com.payments.payments.constants.Constants.ACCEPTED;
import static com.payments.payments.constants.Constants.REJECTED;


@Component
public class PaymentUpdateListener {

    @Autowired
    private ApplicationEventPublisher eventPublisher;



    @Autowired
    PaypalService paypalService;
    @Autowired
    StripeService stripeService;
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
        paymentDetail.setStatus("INITIATED");
        paymentDetailRepository.save(paymentDetail).subscribe();

        String paymentLink = "http://localhost:9999/?sum="+ paymentDetailDTO.getSum() + "&bookingId=" + paymentDetailDTO.getBookingId();
        System.out.println("paymentLink: " + paymentLink);

        paypalService.setPaymentDetail(paymentDetail);
        stripeService.setPaymentDetail(paymentDetail);


//        // Publish the event
//        eventPublisher.publishEvent(new PaymentUpdateEvent(this, paymentDetail));


//        paymentService.verifyPayment(paymentDetail)
//                .doOnSuccess(confirmPayment -> {
//                    if (Boolean.TRUE.equals(confirmPayment)) {
//                        paymentDetail.setStatus(ACCEPTED);
//                        PaymentDetailConfirmationDTO paymentDetailConfirmationDTO =
//                                new PaymentDetailConfirmationDTO(paymentDetail.getBookingId(), true);
//                        paymentDetailRepository.save(paymentDetail).subscribe();
//                        kafkaTemplate.send("payment-request-confirmation", paymentDetailConfirmationDTO);
//                    } else {
//                        paymentDetail.setStatus(REJECTED);
//                        PaymentDetailConfirmationDTO paymentDetailConfirmationDTO =
//                                new PaymentDetailConfirmationDTO(paymentDetail.getBookingId(), false);
//                        paymentDetailRepository.save(paymentDetail).subscribe();
//                        kafkaTemplate.send("payment-request-confirmation", paymentDetailConfirmationDTO);
//                    }
//                })
//                .subscribe();



    }

    @KafkaListener(topics = "payment-request-revert")
    public void listenForRevertPayment(PaymentDetailDTO paymentDetailDTO) {
        log.info("Starting revert process for: {}",paymentDetailDTO);

        String bookingId = paymentDetailDTO.getBookingId();
        paymentService.revertPayment(bookingId)
                .subscribe(
                        result -> log.info("Successfully reverted payment for bookingId: {}", bookingId),
                        error -> log.error("Failed to revert payment for bookingId: {}", bookingId, error)
                );

    }

}