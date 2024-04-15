package com.example.notification.repository;

import com.example.notification.model.Notification;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface NotificationRepository extends ElasticsearchRepository<Notification, String> {
}
