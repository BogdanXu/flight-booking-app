package com.example.notification.service;

import com.example.notification.mapper.NotificationMapper;
import com.example.notification.repository.NotificationRepository;
import com.example.notification.dto.NotificationDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Flux<NotificationDTO> findAll() {
        return notificationRepository.findAll()
                .map(NotificationMapper::toDTO);
    }
}