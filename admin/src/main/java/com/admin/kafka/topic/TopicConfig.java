package com.admin.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {
    @Bean
    NewTopic bookingReserved(){
        return TopicBuilder
                .name("booking-reserved")
                .partitions(10)
                .replicas(1)
                .build();
    }
    @Bean
    NewTopic bookingAdminConfirmation(){
        return TopicBuilder
                .name("booking-admin-confirmation")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    NewTopic bookingRejected(){
        return TopicBuilder
                .name("booking-rejected")
                .partitions(10)
                .replicas(1)
                .build();
    }
}
