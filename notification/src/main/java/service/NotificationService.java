package service;

import dto.NotificationDTO;
import mapper.NotificationMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import repository.NotificationRepository;

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
