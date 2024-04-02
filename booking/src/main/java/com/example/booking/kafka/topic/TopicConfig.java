package com.example.booking.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {
    @Bean
    NewTopic bookingDetails(){
        return TopicBuilder
                .name("booking-details")
                .partitions(10)
                .replicas(1)
                .build();
    }
    @Bean
    NewTopic paymentRequests(){
        return TopicBuilder
                .name("payment-request")
                .partitions(10)
                .replicas(1)
                .build();
    }
    @Bean
    NewTopic notificationEvents(){
        return TopicBuilder
                .name("notification-events")
                .partitions(10)
                .replicas(1)
                .build();
    }
}
