package com.ilim.app.dto.notification;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String status;
    private Long userId;
}