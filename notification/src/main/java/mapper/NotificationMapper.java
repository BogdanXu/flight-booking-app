package mapper;

import dto.NotificationDTO;
import model.Notification;

public class NotificationMapper {

    public Notification toEntity(NotificationDTO dto) {
        if (dto == null) {
            return null;
        }

        Notification entity = new Notification();
        entity.setId(dto.getId());
        entity.setBookingId(dto.getBookingId());
        entity.setMessage(dto.getMessage());
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
        return dto;
    }
}
