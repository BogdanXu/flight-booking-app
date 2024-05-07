package com.example.notification.service;

import com.example.notification.mapper.NotificationMapper;
import com.example.notification.model.Notification;
import com.example.notification.repository.NotificationRepository;
import com.example.notification.dto.NotificationDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    private final MailService mailService;

    public NotificationService(NotificationRepository notificationRepository, MailService mailService) {
        this.notificationRepository = notificationRepository;
        this.mailService = mailService;
    }

    public Iterable<Notification> findAll() {
       return notificationRepository.findAll();
    }

    public Notification saveNotification(Notification notification) {
        mailService.sendEmail(notification.getReceiverEmail(), "Notification Email", notification.getMessage());
        return notificationRepository.save(notification);
    }
}
