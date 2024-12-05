package com.ilim.app.dto.calendar;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateCalendarEventRequest {
    @NotNull(message = "Title cannot be Null")
    private String title;

    @NotNull(message = "Description cannot be Null")
    private String description;

    @NotNull(message = "Start time cannot be null")
    @FutureOrPresent(message = "Start time must be in the present or future")
    private LocalDateTime startTime = LocalDateTime.now();

    @NotNull(message = "End time cannot be null")
    @FutureOrPresent(message = "End time must be in the present or future")
    private LocalDateTime endTime = LocalDateTime.now();

    @NotNull(message = "Event type cannot be null")
    @Pattern(regexp = "LESSON|MEETING|OTHER", message = "Event type must be 'LESSON', 'MEETING', or 'OTHER'")
    private String eventType; // Expecting "LESSON", "MEETING", or "OTHER"

    @NotNull(message = "Lesson ID cannot be null")
    @Min(value = 1, message = "Lesson ID must be greater than 0")
    private Long lessonId;

    @NotNull(message = "Creator ID cannot be null")
    @Min(value = 1, message = "Creator ID must be greater than 0")
    private Long creatorId;

}
