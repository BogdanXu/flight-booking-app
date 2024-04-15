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
                    //Payment confirmation is received first here
                    if(booking.getBookingStatus() == BookingStatus.RESERVED){
                        logger.info("Payment message received first, booking has status RESERVED: {}", bookingId );
                        if(paymentStatus){
                            logger.info("Payment message confirmed, booking has status ACCEPTED_BY_PAYMENT: {}", bookingId );
                            booking.setBookingStatus(BookingStatus.ACCEPTED_BY_PAYMENT);
                        } else {
                            logger.info("Payment message rejected, booking has status REJECTED: {}", bookingId);
                            booking.setBookingStatus(BookingStatus.REJECTED);
                        }
                    }
                    //Payment confirmation comes after Admin has received ok
                    if (booking.getBookingStatus() == BookingStatus.ACCEPTED_BY_ADMIN) {
                        logger.info("Payment message received second, booking has status ACCEPTED_BY_ADMIN: {}", bookingId );
                        if(paymentStatus){
                            booking.setBookingStatus(BookingStatus.SUCCESS);
                            logger.info("Payment message confirmed, booking has status SUCCESS: {}", bookingId );
                        } else {
                            logger.info("Payment message rejected, booking has status REJECTED, sending revert to ADMIN: {}", bookingId );
                            booking.setBookingStatus(BookingStatus.REJECTED);
                            sendRejectedBookingToAdmin(booking);
                        }
                    }
                    kafkaNotificationTemplate.send("notification", new NotificationDTO(booking.getId(),"Booking updated to status "+booking.getBookingStatus()));
                    logger.info("payment saved booking: {}",  booking);
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
        logger.info("received admin message: {}",  bookingMessageDTO);
        String bookingId = bookingMessageDTO.getBookingId();
        boolean confirmed = bookingMessageDTO.getAvailable();
        BookingStatus newBookingStatus = getNewBookingStatus(confirmed);
        bookingRepository.findById(bookingId)
                .flatMap(booking -> {
                    logger.info("found booking: {}",  booking);
                    //Admin confirmation is received first here
                    if(booking.getBookingStatus() == BookingStatus.RESERVED){
                        logger.info("Admin response first, booking has status RESERVED: {} ", bookingId  );
                        if(confirmed){
                            logger.info("Admin confirmed, booking has status ACCEPTED_BY_ADMIN: {} ", bookingId  );
                            booking.setBookingStatus(BookingStatus.ACCEPTED_BY_ADMIN);
                        } else {
                            logger.info("Admin rejected, booking has status REJECTED: {} ", bookingId);
                            booking.setBookingStatus(BookingStatus.REJECTED);
                        }
                    }
                    //Admin confirmation comes after Payment has received ok
                    if (booking.getBookingStatus() == BookingStatus.ACCEPTED_BY_PAYMENT) {
                        logger.info("Admin response second, booking has status ACCEPTED_BY_PAYMENT: {} ", bookingId);
                        booking.setBookingStatus(newBookingStatus);
                        if(!confirmed) {
                            logger.info("reverting payment for: {}",  booking);
                            kafkaPaymentTemplate.send("payment-request-revert", new PaymentDetailDTO(bookingId));
                        }
                    }
                    kafkaNotificationTemplate.send("notification", new NotificationDTO(booking.getId(),"Booking updated to status "+booking.getBookingStatus()));
                    logger.info("admin saved booking: {}",  booking);
                    return bookingRepository.save(booking);
                })
                .subscribe();
    }
}