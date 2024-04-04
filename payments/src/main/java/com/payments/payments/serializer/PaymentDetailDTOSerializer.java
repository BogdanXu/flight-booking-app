package com.payments.payments.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payments.payments.dto.PaymentDetailConfirmationDTO;
import com.payments.payments.dto.PaymentDetailDTO;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class PaymentDetailDTOSerializer implements Serializer<PaymentDetailDTO>, Deserializer<PaymentDetailDTO> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, PaymentDetailDTO data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing Payment object", e);
        }
    }

    @Override
    public PaymentDetailDTO deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, PaymentDetailDTO.class);
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
