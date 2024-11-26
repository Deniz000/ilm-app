package com.ilim.app.business.services;

import com.ilim.app.dto.calendar.CalendarEventResponse;
import com.ilim.app.dto.calendar.CreateCalendarEventRequest;
import com.ilim.app.dto.calendar.UpdateCalendarEventRequest;

import java.util.List;

public interface CalendarEventService {
    List<CalendarEventResponse> getEventsByUser(Long userId);
    List<CalendarEventResponse> getEventsByLesson(Long lessonId);
    CalendarEventResponse createCalendarEvent(CreateCalendarEventRequest request);
    CalendarEventResponse updateCalendarEvent(Long id, UpdateCalendarEventRequest request);
    void deleteCalendarEvent(Long id);
    }
