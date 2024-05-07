package com.example.notification.mapper;

import com.example.notification.dto.NotificationDTO;
import com.example.notification.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public Notification toEntity(NotificationDTO dto) {
        if (dto == null) {
            return null;
        }

        Notification entity = new Notification();
        entity.setId(dto.getId());
        entity.setBookingId(dto.getBookingId());
        entity.setMessage(dto.getMessage());
        entity.setReceiverEmail(dto.getReceiverEmail());
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        return entity;
    }

    public static NotificationDTO toDTO(Notification entity) {
        if (entity == null) {
            return null;
        }

        NotificationDTO dto = new NotificationDTO();
        dto.setId(entity.getId());
        dto.setBookingId(entity.getBookingId());
        dto.setMessage(entity.getMessage());
        dto.setReceiverEmail(entity.getReceiverEmail());
        if (entity.getCreatedAt() != null) {
            dto.setCreatedAt(entity.getCreatedAt());
        }
        return dto;
    }
}
