package com.example.booking.kafka.consumer;

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

    @KafkaListener(topics = "payment-request-updates", groupId = "${kafka.group.id}")
    public void listen(PaymentUpdate paymentUpdate) {
        String bookingId = paymentUpdate.getBookingId();
        PaymentStatus paymentStatus = paymentUpdate.getPaymentStatus();
        BookingStatus newBookingStatus = getNewBookingStatus(paymentStatus);
        bookingRepository.findById(bookingId)
                .flatMap(booking -> {
                    booking.setBookingStatus(newBookingStatus);
                    return bookingRepository.save(booking);
                })
                .subscribe();
    }

    private BookingStatus getNewBookingStatus(PaymentStatus paymentStatus) {
        if (paymentStatus == PaymentStatus.REJECTED) {
            return BookingStatus.REJECTED;
        } else if (paymentStatus == PaymentStatus.SUCCESS) {
            return BookingStatus.SUCCESS;
        } else {
            return BookingStatus.PENDING;
        }
    }
}