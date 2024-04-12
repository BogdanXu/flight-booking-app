package com.payments.payments.mapper;

import com.payments.payments.dto.PaymentDetailDTO;
import com.payments.payments.model.PaymentDetail;

public class PaymentDetailMapper {
    public static PaymentDetailDTO toDTO(PaymentDetail paymentDetail) {
        if (paymentDetail == null) {
            return null;
        }

        PaymentDetailDTO dto = new PaymentDetailDTO();
        dto.setBookingId(paymentDetail.getBookingId());
        dto.setClientIban(paymentDetail.getClientIban());
        dto.setOperatorIban(paymentDetail.getOperatorIban());
        dto.setSum((int) paymentDetail.getAmount());

        return dto;
    }

    public static PaymentDetail fromDTO(PaymentDetailDTO dto) {
        if (dto == null) {
            return null;
        }

        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setBookingId(dto.getBookingId());
        paymentDetail.setClientIban(dto.getClientIban());
        paymentDetail.setOperatorIban(dto.getOperatorIban());
        paymentDetail.setAmount(dto.getSum());

        return paymentDetail;
    }
}
