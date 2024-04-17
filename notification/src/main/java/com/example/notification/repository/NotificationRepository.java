package com.example.notification.repository;

import com.example.notification.model.Notification;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NotificationRepository extends ElasticsearchRepository<Notification, String> {
}
