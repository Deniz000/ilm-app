package com.ilim.app.business.abstracts;

import com.ilim.app.entities.CalendarEvent;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarEventService {
    List<CalendarEvent> getEventsByUser(Long userId);
    List<CalendarEvent> getEventsByLesson(Long lessonId);
    //TODO: dto ile düzeltilecek
    CalendarEvent createCalendarEvent(Long creatorId, String title, String description,
                                             LocalDateTime startTime, LocalDateTime endTime,
                                             CalendarEvent.EventType eventType, Long lessonId);
    }
