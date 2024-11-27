package com.ilim.app.controllers;

import com.ilim.app.business.services.CalendarEventService;
import com.ilim.app.dto.calendar.CalendarEventResponse;
import com.ilim.app.dto.calendar.CreateCalendarEventRequest;
import com.ilim.app.dto.calendar.UpdateCalendarEventRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendar-events")
@RequiredArgsConstructor
public class CalendarEventController {

    private final CalendarEventService calendarEventService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CalendarEventResponse>> getEventsByUser(@Valid @PathVariable Long userId) {
        List<CalendarEventResponse> responses = calendarEventService.getEventsByUser(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<CalendarEventResponse>> getEventsByLesson(@Valid @PathVariable Long lessonId) {
        List<CalendarEventResponse> responses = calendarEventService.getEventsByLesson(lessonId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<CalendarEventResponse> createCalendarEvent(@Valid @RequestBody CreateCalendarEventRequest request) {
        CalendarEventResponse response = calendarEventService.createCalendarEvent(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarEventResponse> updateCalendarEvent(
            @Valid @PathVariable Long id,
            @Valid @RequestBody UpdateCalendarEventRequest request) {
        CalendarEventResponse response = calendarEventService.updateCalendarEvent(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalendarEvent(@Valid @PathVariable Long id) {
        calendarEventService.deleteCalendarEvent(id);
        return ResponseEntity.noContent().build();
    }
}
