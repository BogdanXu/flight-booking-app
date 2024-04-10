package kafka.consumer;

import dto.NotificationDTO;
import mapper.NotificationMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import repository.NotificationRepository;

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
        notificationRepository.save(notificationMapper.toEntity(notificationDTO)).subscribe();
    }
}
