package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.NotificationNotFoundException;
import com.ilim.app.core.exceptions.UserNotFoundException;
import com.ilim.app.dataAccess.NotificationRepository;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.dto.notification.CreateNotificationRequest;
import com.ilim.app.entities.Notification;
import com.ilim.app.entities.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public record NotificationValidationHelper(
        UserRepository userRepository,
        NotificationRepository notificationRepository
) {
    public void validateNotificationRequest(CreateNotificationRequest request) {
        if (!userRepository.existsById(request.getUserId())) {
            throw new UserNotFoundException("User with ID " + request.getUserId() + " does not exist.");
        }
    }

    public void validateUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User with ID " + userId + " does not exist.");
        }
    }
    public UserEntity getUserIfExists(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " does not exist."));
    }

    public Notification getNotificationIfExists(Long id) {
        return notificationRepository.findById(id).orElseThrow(() ->
                new NotificationNotFoundException("Notification with ID " + id + " does not exist."));
    }


}
