package com.payments.payments.kafka.consumer;

import com.payments.payments.dto.PaymentDetailConfirmationDTO;
import com.payments.payments.dto.PaymentDetailDTO;
import com.payments.payments.mapper.PaymentDetailMapper;
import com.payments.payments.model.PaymentDetail;
import com.payments.payments.repository.PaymentDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentUpdateListener {

    private final PaymentDetailRepository paymentDetailRepository;

    private final KafkaTemplate<String, PaymentDetailConfirmationDTO> kafkaTemplate;

    public PaymentUpdateListener(PaymentDetailRepository paymentDetailRepository, KafkaTemplate<String, PaymentDetailConfirmationDTO> kafkaTemplate) {
        this.paymentDetailRepository = paymentDetailRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "payment-request")
    public void listen(PaymentDetailDTO paymentDetailDTO) {

        PaymentDetail paymentDetail = PaymentDetailMapper.fromDTO(paymentDetailDTO);

        if(paymentDetail.getSum() > 100){
            paymentDetail.setStatus("REJECTED");
            PaymentDetailConfirmationDTO paymentDetailConfirmationDTO = new PaymentDetailConfirmationDTO(paymentDetail.getBookingId(), false);
            kafkaTemplate.send("payment-request-confirmation", paymentDetailConfirmationDTO );
        }else {
            paymentDetail.setStatus("Accepted");
            PaymentDetailConfirmationDTO paymentDetailConfirmationDTO = new PaymentDetailConfirmationDTO(paymentDetail.getBookingId(), true);
            kafkaTemplate.send("payment-request-confirmation", paymentDetailConfirmationDTO);
        }
        paymentDetailRepository.save(paymentDetail).subscribe();
    }





}