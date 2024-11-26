package com.ilim.app.business.impl;

import com.ilim.app.business.services.NotificationService;
import com.ilim.app.business.validationhelper.NotificationValidationHelper;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dataAccess.NotificationRepository;
import com.ilim.app.dto.notification.CreateNotificationRequest;
import com.ilim.app.dto.notification.NotificationResponse;
import com.ilim.app.entities.Notification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationValidationHelper validate;
    private final ModelMapperService modelMapperService;

    @Override
    @Transactional
    public NotificationResponse createNotification(CreateNotificationRequest request) {
        log.info("Creating notification for user ID: {}", request.getUserId());

        validate.validateNotificationRequest(request);

        Notification notification = modelMapperService.forRequest().map(request, Notification.class);
        notification.setUser(validate.getUserIfExists(request.getUserId()));

        notificationRepository.save(notification);
        log.info("Notification created with ID: {}", notification.getId());
        return modelMapperService.forResponse().map(notification, NotificationResponse.class);
    }

    @Override
    @Transactional()
    public NotificationResponse getNotificationById(Long id) {
        Notification notification = validate.getNotificationIfExists(id);
        return modelMapperService.forResponse().map(notification, NotificationResponse.class);
    }

    @Override
    @Transactional()
    public List<NotificationResponse> getNotificationsByUser(Long userId) {
        log.info("Fetching notifications for user ID: {}", userId);
        validate.validateUserExists(userId);
        List<Notification> notifications = notificationRepository.findByUserId(userId);

        return notifications.stream().
                map(x -> modelMapperService.forResponse()
                        .map(x, NotificationResponse.class))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public Notification markAsRead(Long id) {
        Notification notification = validate.getNotificationIfExists(id);
        notification.setStatus(Notification.NotificationStatus.READ);
        notificationRepository.save(notification);
        log.info("Notification with ID: {} marked as read.", id);
        return notification;
    }

    @Override
    @Transactional
    public void deleteNotification(Long id) {
        log.info("Deleting notification with ID: {}", id);
        Notification notification = validate.getNotificationIfExists(id);
        notificationRepository.delete(notification);
        log.info("Notification with ID: {} deleted.", id);
    }
}
