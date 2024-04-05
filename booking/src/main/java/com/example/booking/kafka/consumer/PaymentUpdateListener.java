package com.example.booking.kafka.consumer;

import com.example.booking.dto.PaymentDetailConfirmationDTO;
import com.example.booking.model.Booking;
import com.example.booking.model.BookingStatus;
import com.example.booking.model.PaymentStatus;
import com.example.booking.model.PaymentUpdate;
import com.example.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentUpdateListener {

    @Autowired
    private BookingRepository bookingRepository;

    @KafkaListener(topics = "payment-request-confirmation", groupId = "payment_group_id", containerFactory = "paymentConfirmationKafkaListenerContainerFactory")
    public void listen(PaymentDetailConfirmationDTO paymentUpdate) {
        String bookingId = paymentUpdate.getBookingId();
        boolean paymentStatus = paymentUpdate.getPaymentValidation();
        BookingStatus newBookingStatus = getNewBookingStatus(paymentStatus);
        bookingRepository.findById(bookingId)
                .flatMap(booking -> {
                    booking.setBookingStatus(newBookingStatus);
                    return bookingRepository.save(booking);
                })
                .subscribe();
    }

    private BookingStatus getNewBookingStatus(boolean paymentStatus) {
        if (!paymentStatus) {
            return BookingStatus.REJECTED;
        } else
            return BookingStatus.SUCCESS;
    }
}