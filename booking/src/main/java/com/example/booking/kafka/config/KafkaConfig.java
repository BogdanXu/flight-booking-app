package com.example.booking.kafka.config;

import com.example.booking.dto.BookingMessageDTO;
import com.example.booking.dto.PaymentDetailConfirmationDTO;
import com.example.booking.kafka.serializer.BookingMessageDTOSerializer;
import com.example.booking.dto.PaymentDetailDTO;
import com.example.booking.kafka.serializer.PaymentDetailConfirmationDTOSerializer;
import com.example.booking.kafka.serializer.PaymentDetailDTOSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // ProducerFactory and KafkaTemplate for Payment entity
    @Bean
    public ProducerFactory<String, PaymentDetailDTO> paymentProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PaymentDetailDTOSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, PaymentDetailDTO> paymentKafkaTemplate() {
        return new KafkaTemplate<>(paymentProducerFactory());
    }

    // ConsumerFactory and KafkaListenerContainerFactory for Payment entity
    @Bean
    public ConsumerFactory<String, PaymentDetailDTO> paymentConsumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "payment_group_id");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PaymentDetailDTOSerializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new PaymentDetailDTOSerializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentDetailDTO> paymentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentDetailDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentConsumerFactory());
        return factory;
    }

    // ProducerFactory and KafkaTemplate for BookingMessageDTO entity
    @Bean
    public ProducerFactory<String, BookingMessageDTO> bookingProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, BookingMessageDTOSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, BookingMessageDTO> bookingKafkaTemplate() {
        return new KafkaTemplate<>(bookingProducerFactory());
    }

    // ConsumerFactory and KafkaListenerContainerFactory for BookingMessageDTO entity
    @Bean
    public ConsumerFactory<String, BookingMessageDTO> bookingConsumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "booking_group_id");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, BookingMessageDTOSerializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new BookingMessageDTOSerializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookingMessageDTO> bookingKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BookingMessageDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(bookingConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, PaymentDetailConfirmationDTO> paymentDetailConfirmationDTOConsumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "payment_group_id");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PaymentDetailConfirmationDTOSerializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new PaymentDetailConfirmationDTOSerializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentDetailConfirmationDTO> paymentConfirmationKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentDetailConfirmationDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentDetailConfirmationDTOConsumerFactory());
        return factory;
    }
}