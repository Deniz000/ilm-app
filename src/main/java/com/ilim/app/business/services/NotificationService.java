package com.ilim.app.business.services;

import com.ilim.app.dto.notification.CreateNotificationRequest;
import com.ilim.app.dto.notification.NotificationResponse;
import com.ilim.app.entities.Notification;

import java.util.List;

public interface NotificationService {
    NotificationResponse createNotification(CreateNotificationRequest notification);
    NotificationResponse getNotificationById(Long id);
    List<NotificationResponse> getNotificationsByUser(Long userId);
    Notification markAsRead(Long id);
    void deleteNotification(Long id);
}

