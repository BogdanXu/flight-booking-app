package com.example.booking.kafka.serializer;

import com.example.booking.dto.PaymentDetailConfirmationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class PaymentDetailConfirmationDTOSerializer implements Serializer<PaymentDetailConfirmationDTO>, Deserializer<PaymentDetailConfirmationDTO> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, PaymentDetailConfirmationDTO data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing Payment object", e);
        }
    }

    @Override
    public PaymentDetailConfirmationDTO deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, PaymentDetailConfirmationDTO.class);
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