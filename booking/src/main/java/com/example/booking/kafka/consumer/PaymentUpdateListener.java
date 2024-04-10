package com.example.booking.kafka.consumer;

import com.example.booking.dto.BookingMessageDTO;
import com.example.booking.dto.PaymentDetailConfirmationDTO;
import com.example.booking.model.Booking;
import com.example.booking.model.BookingStatus;
import com.example.booking.repository.BookingRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentUpdateListener {

    private final BookingRepository bookingRepository;
    private final KafkaTemplate<String, BookingMessageDTO> kafkaAdminTemplate;

    public PaymentUpdateListener(BookingRepository bookingRepository, KafkaTemplate<String, BookingMessageDTO> kafkaAdminTemplate) {
        this.bookingRepository = bookingRepository;
        this.kafkaAdminTemplate = kafkaAdminTemplate;
    }

    @KafkaListener(topics = "payment-request-confirmation", groupId = "payment_group_id", containerFactory = "paymentConfirmationKafkaListenerContainerFactory")
    public void listen(PaymentDetailConfirmationDTO paymentUpdate) {
        String bookingId = paymentUpdate.getBookingId();
        boolean paymentStatus = paymentUpdate.getPaymentValidation();
        bookingRepository.findById(bookingId)
                .flatMap(booking -> {
                    if(booking.getBookingStatus() == BookingStatus.RESERVED){
                        if(paymentStatus){
                            booking.setBookingStatus(BookingStatus.ACCEPTED_BY_PAYMENT);
                        } else
                            booking.setBookingStatus(BookingStatus.REJECTED);
                    }
                    if (booking.getBookingStatus() == BookingStatus.ACCEPTED_BY_ADMIN) {
                        if(paymentStatus){
                            booking.setBookingStatus(BookingStatus.SUCCESS);
                        } else {
                            booking.setBookingStatus(BookingStatus.REJECTED);
                            sendRejectedBookingToAdmin(booking);
                        }
                    }
                    return bookingRepository.save(booking);
                })
                .subscribe();
    }

    private void sendRejectedBookingToAdmin(Booking booking){
        kafkaAdminTemplate.send("booking-rejected", new BookingMessageDTO(booking.getId(), booking.getFlight().getId(), booking.getSeats().size()));
    }

    private BookingStatus getNewBookingStatus(boolean paymentStatus) {
        if (!paymentStatus) {
            return BookingStatus.REJECTED;
        } else
            return BookingStatus.SUCCESS;
    }

    @KafkaListener(topics = "booking-admin-confirmation", groupId = "booking_group_id", containerFactory = "bookingKafkaListenerContainerFactory")
    public void listen(BookingMessageDTO bookingMessageDTO) {
        String bookingId = bookingMessageDTO.getBookingId();
        boolean confirmationStatus = bookingMessageDTO.getAvailable();
        BookingStatus newBookingStatus = getNewBookingStatus(confirmationStatus);
        bookingRepository.findById(bookingId)
                .flatMap(booking -> {
                    if(booking.getBookingStatus() == BookingStatus.RESERVED){
                        if(confirmationStatus){
                            booking.setBookingStatus(BookingStatus.ACCEPTED_BY_ADMIN);
                        } else
                            booking.setBookingStatus(BookingStatus.REJECTED);
                    }
                    if (booking.getBookingStatus() == BookingStatus.ACCEPTED_BY_PAYMENT) {
                        booking.setBookingStatus(newBookingStatus);
                    }
                    return bookingRepository.save(booking);
                })
                .subscribe();
    }
}