package com.example.booking.kafka.consumer;

import com.example.booking.dto.BookingMessageDTO;
import com.example.booking.dto.NotificationDTO;
import com.example.booking.dto.PaymentDetailConfirmationDTO;
import com.example.booking.dto.PaymentDetailDTO;
import com.example.booking.model.Booking;
import com.example.booking.model.BookingStatus;
import com.example.booking.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookingUpdateListener {

    private final BookingRepository bookingRepository;
    private final KafkaTemplate<String, BookingMessageDTO> kafkaAdminTemplate;
    private final KafkaTemplate<String, NotificationDTO> kafkaNotificationTemplate;
    private final KafkaTemplate<String, PaymentDetailDTO> kafkaPaymentTemplate;

    private final Logger logger = LoggerFactory.getLogger(BookingUpdateListener.class);

    public BookingUpdateListener(BookingRepository bookingRepository, KafkaTemplate<String, BookingMessageDTO> kafkaAdminTemplate, KafkaTemplate<String, NotificationDTO> kafkaNotificationTemplate, KafkaTemplate<String, PaymentDetailDTO> kafkaPaymentTemplate) {
        this.bookingRepository = bookingRepository;
        this.kafkaAdminTemplate = kafkaAdminTemplate;
        this.kafkaNotificationTemplate = kafkaNotificationTemplate;
        this.kafkaPaymentTemplate = kafkaPaymentTemplate;
    }

    @KafkaListener(topics = "payment-request-confirmation", groupId = "payment_group_id", containerFactory = "paymentConfirmationKafkaListenerContainerFactory")
    public void listen(PaymentDetailConfirmationDTO paymentUpdate) {
        String bookingId = paymentUpdate.getBookingId();
        boolean paymentStatus = paymentUpdate.getPaymentValidation();
        bookingRepository.findById(bookingId)
                .flatMap(booking -> {
                    //Payment confirmation is received before Admin answered
                    if(booking.getBookingStatus() == BookingStatus.RESERVED){
                        logger.info("Payment message received first, booking has status RESERVED: {}", bookingId);
                        if(paymentStatus){
                            logger.info("Payment message confirmed, booking changed status to ACCEPTED_BY_PAYMENT: {}", bookingId);
                            booking.setBookingStatus(BookingStatus.ACCEPTED_BY_PAYMENT);
                        } else {
                            logger.info("Payment message rejected, booking changed status to REJECTED: {}", bookingId);
                            booking.setBookingStatus(BookingStatus.REJECTED_BY_PAYMENT);
                        }
                    }
                    //Payment confirmation comes after Admin has accepted the reservation
                    if (booking.getBookingStatus() == BookingStatus.ACCEPTED_BY_ADMIN) {
                        logger.info("Payment message received second, booking has status ACCEPTED_BY_ADMIN: {}", bookingId);
                        if(paymentStatus){
                            booking.setBookingStatus(BookingStatus.SUCCESS);
                            logger.info("Payment message confirmed, booking changed status to SUCCESS: {}", bookingId);
                        } else {
                            logger.info("Payment message rejected, booking changed status to REJECTED, sending revert to Admin: {}", bookingId);
                            booking.setBookingStatus(BookingStatus.REJECTED);
                            sendRejectedBookingToAdmin(booking);
                        }
                    }
                    //Payment confirmation comes after Admin has rejected the reservation
                    if(booking.getBookingStatus() == BookingStatus.REJECTED_BY_ADMIN){
                        logger.info("Payment message received second, booking has status REJECTED_BY_ADMIN: {}", bookingId);
                        if(paymentStatus){
                            logger.info("Payment was accepted but Admin rejected, booking changed status to REJECTED, sending revert to Payment: {}", bookingId);
                            booking.setBookingStatus(BookingStatus.REJECTED);
                            revertPaymentProcessing(booking);
                        }
                        else {
                            logger.info("Both validations rejected, booking changed status to REJECTED: {}", bookingId);
                            booking.setBookingStatus(BookingStatus.REJECTED);
                        }

                    }

                    kafkaNotificationTemplate.send("notification", new NotificationDTO(booking.getId(),"Booking updated to status "+booking.getBookingStatus()));
                    logger.info("Booking state after payment processing: {}",  booking);
                    return bookingRepository.save(booking);
                })
                .subscribe();
    }

    private void sendRejectedBookingToAdmin(Booking booking){
        kafkaAdminTemplate.send("booking-rejected", new BookingMessageDTO(booking.getId(), booking.getFlight().getId(), booking.getSeats().size()));
    }

    private void revertPaymentProcessing(Booking booking){
        kafkaPaymentTemplate.send("payment-request-revert", new PaymentDetailDTO(booking.getId()));
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
        boolean confirmed = bookingMessageDTO.getAvailable();
        BookingStatus newBookingStatus = getNewBookingStatus(confirmed);
        bookingRepository.findById(bookingId)
                .flatMap(booking -> {
                    //Admin confirmation is received first here
                    if(booking.getBookingStatus() == BookingStatus.RESERVED){
                        logger.info("Admin response first, booking has status RESERVED: {} ", bookingId  );
                        if(confirmed){
                            logger.info("Admin confirmed, booking changed status to ACCEPTED_BY_ADMIN: {} ", bookingId  );
                            booking.setBookingStatus(BookingStatus.ACCEPTED_BY_ADMIN);
                        } else {
                            logger.info("Admin rejected, booking changed status to REJECTED: {} ", bookingId);
                            booking.setBookingStatus(BookingStatus.REJECTED_BY_ADMIN);
                        }
                    }
                    //Admin confirmation comes after Payment has received ok
                    if (booking.getBookingStatus() == BookingStatus.ACCEPTED_BY_PAYMENT) {
                        logger.info("Admin response second, booking has status ACCEPTED_BY_PAYMENT: {} ", bookingId);
                        booking.setBookingStatus(newBookingStatus);
                        if(!confirmed) {
                            logger.info("Admin message rejected, booking changed status to REJECTED, sending revert to Payment: {}",  booking);
                            kafkaPaymentTemplate.send("payment-request-revert", new PaymentDetailDTO(bookingId));
                        }
                    }
                    //Admin confirmation comes after Payment has rejected the reservation
                    if(booking.getBookingStatus() == BookingStatus.REJECTED_BY_PAYMENT){
                        logger.info("Admin message received second, booking has status REJECTED_BY_PAYMENT: {}", bookingId);
                        if(confirmed){
                            logger.info("Admin was accepted but Payment was rejected, booking changed status to REJECTED, sending revert to Admin: {}", bookingId);
                            booking.setBookingStatus(BookingStatus.REJECTED);
                            revertPaymentProcessing(booking);
                        }
                        else {
                            logger.info("Both validations rejected, booking changed status to REJECTED: {}", bookingId);
                            booking.setBookingStatus(BookingStatus.REJECTED);
                        }
                    }
                    kafkaNotificationTemplate.send("notification", new NotificationDTO(booking.getId(),"Booking updated to status "+booking.getBookingStatus()));
                    logger.info("Booking state after admin processing: {}",  booking);
                    return bookingRepository.save(booking);
                })
                .subscribe();
    }
}