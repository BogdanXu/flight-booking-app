package kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.NotificationDTO;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class NotificationSerializer implements Serializer<NotificationDTO>, Deserializer<NotificationDTO> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No additional configuration needed
    }

    @Override
    public byte[] serialize(String topic, NotificationDTO data) {
        if (data == null)
            return null;
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error serializing BookingMessageDTO: " + e.getMessage(), e);
        }
    }

    @Override
    public NotificationDTO deserialize(String topic, byte[] data) {
        if (data == null)
            return null;
        try {
            return objectMapper.readValue(data, NotificationDTO.class);
        } catch (IOException e) {
            throw new SerializationException("Error deserializing BookingMessageDTO: " + e.getMessage(), e);
        }
    }

    @Override
    public void close() {
        // Nothing to close
    }
}
