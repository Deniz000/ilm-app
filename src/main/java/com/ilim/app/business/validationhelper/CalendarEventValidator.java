package com.ilim.app.business.validationhelper;

import com.ilim.app.entities.CalendarEvent;

import java.util.List;

public interface CalendarEventValidator extends Validator<CalendarEvent> {
    List<CalendarEvent> getCalendarEventsByLessonId(Long lessonId);
}
