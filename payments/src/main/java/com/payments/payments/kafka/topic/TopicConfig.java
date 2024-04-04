package com.payments.payments.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig  {
    @Bean
    NewTopic paymentRequest(){
        return TopicBuilder
                .name("payment-request")
                .partitions(10)
                .replicas(1)
                .build();
    }
    @Bean
    NewTopic paymentRequestConfirmation(){
        return TopicBuilder
                .name("payment-request-confirmation")
                .partitions(10)
                .replicas(1)
                .build();
    }

}