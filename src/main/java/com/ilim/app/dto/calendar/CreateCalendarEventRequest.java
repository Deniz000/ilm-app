package com.ilim.app.dto.calendar;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateCalendarEventRequest {
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String eventType; // Expecting "LESSON", "MEETING", or "OTHER"
    private Long lessonId;
    private Long creatorId;
}
