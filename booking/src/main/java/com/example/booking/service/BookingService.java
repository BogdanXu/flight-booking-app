package com.example.booking.service;

import com.example.booking.dto.*;
import com.example.booking.mappers.BookingMapper;
import com.example.booking.model.Booking;
import com.example.booking.model.BookingStatus;
import com.example.booking.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final int expirationTime = 1;

    private final KafkaTemplate<String, PaymentDetailDTO> kafkaPaymentTemplate;
    private final KafkaTemplate<String, BookingMessageDTO> kafkaAdminTemplate;
    private final KafkaTemplate<String, NotificationDTO> kafkaNotificationTemplate;

    private final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;

    public BookingService(KafkaTemplate<String, PaymentDetailDTO> kafkaPaymentTemplate, KafkaTemplate<String, BookingMessageDTO> kafkaAdminTemplate, KafkaTemplate<String, NotificationDTO> kafkaNotificationTemplate, BookingRepository bookingRepository) {
        this.kafkaPaymentTemplate = kafkaPaymentTemplate;
        this.kafkaAdminTemplate = kafkaAdminTemplate;
        this.kafkaNotificationTemplate = kafkaNotificationTemplate;
        this.bookingRepository = bookingRepository;
    }

    public Mono<BookingDTO> saveBooking(ExtendedBookingDTO bookingDTO) {
        bookingDTO.setBookingStatus(BookingStatus.RESERVED);
        bookingDTO.setBookingDate(LocalDateTime.now());
        bookingDTO.setExpirationDate(LocalDateTime.now().plusHours(expirationTime));

        String clientIban = bookingDTO.getClientIban();
        String operatorIban = bookingDTO.getOperatorIban();
        Integer sum = bookingDTO.getSum();

        return bookingRepository.save(BookingMapper.fromDTO(bookingDTO))
                .doOnSuccess(booking -> {
                    kafkaNotificationTemplate.send("notification", new NotificationDTO(booking.getId(),"Booking updated to status "+booking.getBookingStatus()));
                    sendPaymentRequest(booking.getId(), clientIban, operatorIban, sum);
                    sendAdminConfirmation(new BookingMessageDTO(booking.getId(), booking.getFlight().getId(), booking.getSeats().size()));
                })
                .map(BookingMapper::toDTO);
    }

    public Mono<BookingDTO> findById(String id) {
        return bookingRepository.findById(id)
                .map(BookingMapper::toDTO);
    }

    private void sendPaymentRequest(String bookingId, String clientIban, String operatorIban, Integer sum) {
        PaymentDetailDTO paymentDetailDTO = new PaymentDetailDTO(bookingId, clientIban, operatorIban, sum);
        kafkaPaymentTemplate.send("payment-request", paymentDetailDTO);
        logger.info("Sent message to Kafka topic payment-request: {}", paymentDetailDTO);
    }

    private void sendAdminConfirmation(BookingMessageDTO bookingMessageDTO){
        kafkaAdminTemplate.send("booking-reserved",bookingMessageDTO);
        logger.info("Sent message to Kafka topic booking-reserved: {}", bookingMessageDTO);
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void checkExpiredBookings() {
        logger.info("Cron job running...");
        bookingRepository.findBookingsByBookingStatusNotInAndExpirationDateBefore(List.of(BookingStatus.SUCCESS, BookingStatus.REJECTED), LocalDateTime.now())
                .doOnNext(booking -> {
                    logger.info("Booking reservation expired: {}", booking);
                    logger.info("Setting booking status to rejected for booking: {}", booking.getId());
                    booking.setBookingStatus(BookingStatus.REJECTED);
                    sendRejectedBookingToAdmin(booking);
                    kafkaNotificationTemplate.send("notification", new NotificationDTO(booking.getId(),"Booking updated to status "+booking.getBookingStatus()));
                    bookingRepository.save(booking).subscribe();
                })
                .subscribe();
    }

    private void sendRejectedBookingToAdmin(Booking booking){
        kafkaAdminTemplate.send("booking-rejected", new BookingMessageDTO(booking.getId(), booking.getFlight().getId(), booking.getSeats().size()));
    }

}









