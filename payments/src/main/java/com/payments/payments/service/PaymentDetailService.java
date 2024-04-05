package com.payments.payments.service;

import com.payments.payments.dto.PaymentDetailConfirmationDTO;
import com.payments.payments.dto.PaymentDetailDTO;
import com.payments.payments.model.PaymentDetail;
import com.payments.payments.repository.PaymentDetailRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class PaymentDetailService {


    private final PaymentDetailRepository paymentDetailRepository;

    public PaymentDetailService(PaymentDetailRepository paymentDetailRepository) {
        this.paymentDetailRepository = paymentDetailRepository;
    }

        public Mono<PaymentDetail> savePaymentDetail(PaymentDetail paymentDetail) {
        return paymentDetailRepository.save(paymentDetail);
    }

//    public Mono<PaymentDetailConfirmationDTO> processPayment(PaymentDetailDTO paymentDTO) {
//        return Mono.fromSupplier(() -> simulatePayment())
//                .flatMap(paymentSuccess -> {
//
//                    PaymentDetail paymentDetail = new PaymentDetail();
//                    paymentDetail.setBookingId(paymentDTO.getBookingId());
//                    paymentDetail.setClientIban(paymentDTO.getClientIban());
//                    paymentDetail.setOperatorIban(paymentDTO.getOperatorIban());
//                    paymentDetail.setSum(paymentDTO.getSum());
//                    paymentDetail.setPaymentInitiationDate(LocalDateTime.now());
//
//                    if (paymentSuccess) {
//                        paymentDetail.setStatus("SUCCESS");
//                        paymentDetail.setPaymentValidation(true);
//                    } else {
//                        paymentDetail.setStatus("REJECTED");
//                        paymentDetail.setPaymentValidation(false);
//                    }
//
//                    return paymentDetailRepository.save(paymentDetail);
//                })
//                .map(savedPaymentDetail -> {
//
//                    PaymentDetailConfirmationDTO confirmationDTO = new PaymentDetailConfirmationDTO();
//                    confirmationDTO.setBookingId(savedPaymentDetail.getBookingId());
//                    confirmationDTO.setPaymentValidation(savedPaymentDetail.getPaymentValidation());
//                    return confirmationDTO;
//                });
//    }
//
//    private boolean simulatePayment() {
//        return Math.random() > 0.5;
//    }
}
