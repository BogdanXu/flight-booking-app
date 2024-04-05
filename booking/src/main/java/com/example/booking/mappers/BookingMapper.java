package com.example.booking.mappers;

import com.example.booking.dto.BookingDTO;
import com.example.booking.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    private BookingMapper(){
    }

    public static BookingDTO toDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setFlight(FlightMapper.toDTO(booking.getFlight()));
        bookingDTO.setBookingDate(booking.getBookingDate());
        bookingDTO.setExpirationDate(booking.getExpirationDate());
        bookingDTO.setSeatNumber(booking.getSeatNumber());
        bookingDTO.setBookingStatus(booking.getBookingStatus());
        return bookingDTO;
    }

    public static Booking fromDTO(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setFlight(FlightMapper.fromDTO(bookingDTO.getFlight()));
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setExpirationDate(bookingDTO.getExpirationDate());
        booking.setSeatNumber(bookingDTO.getSeatNumber());
        booking.setBookingStatus(bookingDTO.getBookingStatus());
        return booking;
    }
}