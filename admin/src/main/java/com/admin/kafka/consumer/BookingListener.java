package com.admin.kafka.consumer;

import com.admin.dto.BookingMessageDTO;
import com.admin.model.Flight;
import com.admin.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookingListener {

    @Autowired
    private FlightRepository flightRepository;

    private final KafkaTemplate<String, BookingMessageDTO> kafkaTemplate;

    public BookingListener(KafkaTemplate<String, BookingMessageDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "booking-reserved", groupId = "${kafka.group.id}")
    public void listenForBookings(BookingMessageDTO bookingMessageDTO) {
        Long flightId = bookingMessageDTO.getFlightId();
        Flight flight = null;
        if(flightRepository.findById(flightId).isPresent()){
            flight = flightRepository.findById(flightId).get();
            if(flight.getSeatsAvailable() - bookingMessageDTO.getNumberOfSeats()>=0){
                flight.setSeatsAvailable(flight.getSeatsAvailable() - bookingMessageDTO.getNumberOfSeats());
                flightRepository.save(flight);
                BookingMessageDTO bookingAccepted = new BookingMessageDTO(bookingMessageDTO.getBookingId(), true);
                kafkaTemplate.send("booking-admin-confirmation", bookingAccepted);
            } else {
                BookingMessageDTO bookingRejected = new BookingMessageDTO(bookingMessageDTO.getBookingId(), false);
                kafkaTemplate.send("booking-admin-confirmation", bookingRejected);
            }
        }
    }

    @KafkaListener(topics = "booking-rejected", groupId = "${kafka.group.id}")
    public void listenForCanceledBookings(BookingMessageDTO bookingMessageDTO) {
        Long flightId = bookingMessageDTO.getFlightId();
        Flight flight = null;
        if(flightRepository.findById(flightId).isPresent()){
            flight = flightRepository.findById(flightId).get();
            flight.setSeatsAvailable(flight.getSeatsAvailable() + bookingMessageDTO.getNumberOfSeats());
            flightRepository.save(flight);
        }
    }
}
