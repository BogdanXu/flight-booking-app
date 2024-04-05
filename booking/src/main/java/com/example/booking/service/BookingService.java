package com.example.booking.service;

import com.example.booking.dto.BookingDTO;
import com.example.booking.mappers.BookingMapper;
import com.example.booking.model.BookingStatus;
import com.example.booking.dto.PaymentDetailDTO;
import com.example.booking.repository.BookingRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class BookingService {

    private final int expirationTime = 1;

    private final KafkaTemplate<String, PaymentDetailDTO> kafkaTemplate;

    private final BookingRepository bookingRepository;

    public BookingService(KafkaTemplate<String, PaymentDetailDTO> kafkaTemplate, BookingRepository bookingRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.bookingRepository = bookingRepository;
    }

    public Mono<BookingDTO> saveBooking(BookingDTO bookingDTO) {
        bookingDTO.setBookingStatus(BookingStatus.RESERVED);
        bookingDTO.setBookingDate(LocalDateTime.now());
        bookingDTO.setExpirationDate(LocalDateTime.now().plusHours(expirationTime));

        return bookingRepository.save(BookingMapper.fromDTO(bookingDTO))
                .doOnSuccess(booking -> sendPaymentRequest(booking.getId()))
                .map(BookingMapper::toDTO);
    }

    public Mono<BookingDTO> findById(String id) {
        return bookingRepository.findById(id)
                .map(BookingMapper::toDTO);
    }

    private void sendPaymentRequest(String bookingId) {
        PaymentDetailDTO paymentDetailDTO = new PaymentDetailDTO(bookingId, "clientIban", "operatorIban", 100);
        kafkaTemplate.send("payment-request", paymentDetailDTO);
        System.out.println("Sent message to Kafka topic payment-request:" + paymentDetailDTO);
    }

}