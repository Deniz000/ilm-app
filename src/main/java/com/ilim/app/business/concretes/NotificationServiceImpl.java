package com.ilim.app.business.concretes;

import com.ilim.app.business.abstracts.NotificationService;
import com.ilim.app.dataAccess.NotificationRepository;
import com.ilim.app.entities.Notification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification createNotification(Notification notification) {
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found with ID: " + id));
    }

    @Override
    public List<Notification> getNotificationsByUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public Notification markAsRead(Long id) {
        Notification notification = getNotificationById(id);
        notification.setStatus(Notification.NotificationStatus.READ);
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
