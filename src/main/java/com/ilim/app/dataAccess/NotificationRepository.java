package com.ilim.app.dataAccess;

import com.ilim.app.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

//Redundant actually
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
