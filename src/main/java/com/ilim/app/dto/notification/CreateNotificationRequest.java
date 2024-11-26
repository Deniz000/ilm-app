package com.ilim.app.dto.notification;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationRequest {
    @NotNull
    private String content;

    @NotNull
    private Long userId;

    @NotNull
    private String status;
}
