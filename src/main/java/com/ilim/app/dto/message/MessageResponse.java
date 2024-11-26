package com.ilim.app.dto.message;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String messageContent;
    private LocalDateTime createdAt;
}