package com.ilim.app.business.services;

import com.ilim.app.dto.calendar.CalendarEventResponse;
import com.ilim.app.dto.calendar.CreateCalendarEventRequest;
import com.ilim.app.dto.calendar.UpdateCalendarEventRequest;

import java.util.List;

public interface CalendarEventService {

    CalendarEventResponse createCalendarEvent(CreateCalendarEventRequest request);

    CalendarEventResponse updateCalendarEvent(Long id, UpdateCalendarEventRequest request);

    void deleteCalendarEvent(Long id);

    List<CalendarEventResponse> getAllEventsByStudent(Long userId);
    List<CalendarEventResponse> getAllEventsByTeacher(Long userId);

    List<CalendarEventResponse> getFilteredEvents(String day, String week, Boolean upcoming);


}
