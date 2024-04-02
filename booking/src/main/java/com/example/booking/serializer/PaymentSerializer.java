package com.example.booking.serializer;

import com.example.booking.model.Payment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class PaymentSerializer implements Serializer<Payment>, Deserializer<Payment> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Payment data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing Payment object", e);
        }
    }

    @Override
    public Payment deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, Payment.class);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing Payment object", e);
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No additional configuration required
    }

    @Override
    public void close() {
        // No resources to close
    }
}