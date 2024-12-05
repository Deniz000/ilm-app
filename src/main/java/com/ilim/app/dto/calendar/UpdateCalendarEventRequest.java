package com.ilim.app.dto.calendar;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateCalendarEventRequest {
    @NotNull(message = "id will come by default, bu ypu have to give it anyway")
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String eventType;
    private Long lessonId;
}
