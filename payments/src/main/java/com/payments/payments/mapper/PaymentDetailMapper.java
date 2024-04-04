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
        // Assuming clientIban maps to iban in PaymentDetail
        dto.setClientIban(paymentDetail.getIban());
        // operatorIban and sum need to be set based on your logic, they might not directly map
        dto.setOperatorIban(null); // Set this based on your application's logic
        dto.setSum((int) paymentDetail.getAmount()); // Be careful with casting, ensure this is what you want

        return dto;
    }

    public static PaymentDetail fromDTO(PaymentDetailDTO dto) {
        if (dto == null) {
            return null;
        }

        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setBookingId(dto.getBookingId());
        paymentDetail.setIban(dto.getClientIban()); // Assuming iban maps to clientIban in DTO
        // operatorIban is not stored in PaymentDetail
        paymentDetail.setAmount(dto.getSum()); // Make sure the types are compatible
        // You'll need to set other fields like id, paymentInitiationDate, and status as needed

        return paymentDetail;
    }
}
