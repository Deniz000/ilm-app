package com.ilim.app.dto.message;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {
    @NotNull
    private Long senderId;

    @NotNull
    private Long receiverId;

    @NotNull
    private String messageContent;
}