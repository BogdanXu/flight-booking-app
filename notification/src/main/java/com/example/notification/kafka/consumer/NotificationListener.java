package com.example.notification.kafka.consumer;

import com.example.notification.dto.NotificationDTO;
import com.example.notification.mapper.NotificationMapper;
import com.example.notification.repository.NotificationRepository;
import com.example.notification.service.MailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class NotificationListener {
    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    private final MailService mailService;

    public NotificationListener(NotificationRepository notificationRepository, NotificationMapper notificationMapper, MailService mailService) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.mailService = mailService;
    }

    @KafkaListener(topics = "notification")
    public void listenForCanceledBookings(NotificationDTO notificationDTO) {
        notificationDTO.setCreatedAt(new Date());
        mailService.sendEmail(notificationDTO.getReceiverEmail(), "Notification Email", notificationDTO.getMessage());
        notificationRepository.save(notificationMapper.toEntity(notificationDTO));
    }
}
