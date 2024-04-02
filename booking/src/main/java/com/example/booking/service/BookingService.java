package com.example.booking.service;

import com.example.booking.dto.BookingDTO;
import com.example.booking.model.Booking;
import com.example.booking.model.BookingStatus;
import com.example.booking.model.Payment;
import com.example.booking.repository.BookingRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BookingService {

    private final KafkaTemplate<String, Payment> kafkaTemplate;

    private final BookingRepository bookingRepository;

    public BookingService(KafkaTemplate<String, Payment> kafkaTemplate, BookingRepository bookingRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.bookingRepository = bookingRepository;
    }

    public Mono<BookingDTO> saveBooking(BookingDTO bookingDTO) {
        bookingDTO.setBookingStatus(BookingStatus.RESERVED);
        return bookingRepository.save(mapToEntity(bookingDTO))
                .doOnSuccess(booking -> sendPaymentRequest(booking.getId()))
                .map(this::mapToDTO);
    }

    public Mono<BookingDTO> findById(String id) {
        return bookingRepository.findById(id)
                .map(this::mapToDTO);
    }

    private void sendPaymentRequest(String bookingId) {
        Payment payment = new Payment(bookingId, "clientIban", "operatorIban", 100);
        kafkaTemplate.send("payment-request", payment);
        System.out.println("Sent message to Kafka topic payment-request:" + payment);
    }

    private Booking mapToEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setFlight(bookingDTO.getFlight());
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setExpirationDate(bookingDTO.getExpirationDate());
        booking.setSeatNumber(bookingDTO.getSeatNumber());
        booking.setBookingStatus(bookingDTO.getBookingStatus());
        return booking;
    }

    private BookingDTO mapToDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setFlight(booking.getFlight());
        bookingDTO.setBookingDate(booking.getBookingDate());
        bookingDTO.setExpirationDate(booking.getExpirationDate());
        bookingDTO.setSeatNumber(booking.getSeatNumber());
        bookingDTO.setBookingStatus(booking.getBookingStatus());
        return bookingDTO;
    }
}