package com.ilim.app.dataAccess;

import com.ilim.app.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Redundant actually
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification>  findByUserId(Long userId);
}
