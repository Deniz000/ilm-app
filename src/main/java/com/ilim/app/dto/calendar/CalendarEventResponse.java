package com.ilim.app.dto.calendar;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CalendarEventResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String eventType; // e.g., "LESSON", "MEETING", or "OTHER"
    private Long lessonId; // Null if not related to a lesson
    private Long creatorId;
}
