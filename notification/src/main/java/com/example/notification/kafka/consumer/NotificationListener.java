package com.example.notification.kafka.consumer;

import com.example.notification.dto.NotificationDTO;
import com.example.notification.mapper.NotificationMapper;
import com.example.notification.repository.NotificationRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    public NotificationListener(NotificationRepository notificationRepository, NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }

    @KafkaListener(topics = "notification")
    public void listenForCanceledBookings(NotificationDTO notificationDTO) {
        notificationRepository.save(notificationMapper.toEntity(notificationDTO));
    }
}
