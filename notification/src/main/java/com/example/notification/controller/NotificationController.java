package com.example.notification.controller;

import com.example.notification.dto.NotificationDTO;
import com.example.notification.model.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import com.example.notification.service.NotificationService;

import java.util.Date;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping()
    public Iterable<Notification> getNotificationById() {
        return notificationService.findAll();
    }

    @PostMapping()
    public ResponseEntity<String> saveNotification(@RequestBody Notification notification) {
        try {
            notification.setCreatedAt(new Date());
            Notification savedNotification = notificationService.saveNotification(notification);
            return new ResponseEntity<>("Notification saved with ID: " + savedNotification.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
