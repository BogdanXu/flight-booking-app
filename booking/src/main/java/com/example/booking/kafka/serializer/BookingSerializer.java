package com.example.booking.kafka.serializer;

import com.example.booking.dto.BookingMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class BookingSerializer implements Serializer<BookingMessageDTO>, Deserializer<BookingMessageDTO> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No additional configuration needed
    }

    @Override
    public byte[] serialize(String topic, BookingMessageDTO data) {
        if (data == null)
            return null;
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error serializing BookingMessageDTO: " + e.getMessage(), e);
        }
    }

    @Override
    public BookingMessageDTO deserialize(String topic, byte[] data) {
        if (data == null)
            return null;
        try {
            return objectMapper.readValue(data, BookingMessageDTO.class);
        } catch (IOException e) {
            throw new SerializationException("Error deserializing BookingMessageDTO: " + e.getMessage(), e);
        }
    }

    @Override
    public void close() {
        // Nothing to close
    }
}