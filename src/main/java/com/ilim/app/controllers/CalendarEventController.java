package com.ilim.app.controllers;

import com.ilim.app.business.services.CalendarEventService;
import com.ilim.app.dto.calendar.CalendarEventResponse;
import com.ilim.app.dto.calendar.CreateCalendarEventRequest;
import com.ilim.app.dto.calendar.UpdateCalendarEventRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendar-events")
@RequiredArgsConstructor
public class CalendarEventController {

    private final CalendarEventService calendarEventService;

    @PostMapping
    public ResponseEntity<CalendarEventResponse> createCalendarEvent(@Valid @RequestBody CreateCalendarEventRequest request) {
        CalendarEventResponse response = calendarEventService.createCalendarEvent(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarEventResponse> updateCalendarEvent(
            @Valid @RequestBody UpdateCalendarEventRequest request) {
        CalendarEventResponse response = calendarEventService.updateCalendarEvent(request.getId(), request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalendarEvent(@Valid @PathVariable Long id) {
        calendarEventService.deleteCalendarEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{teacherId}")
    public ResponseEntity<List<CalendarEventResponse>> getEventsByTeacher(@Valid @PathVariable Long teacherId) {
        List<CalendarEventResponse> responses = calendarEventService.getAllEventsByTeacher(teacherId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{studentId}")
    public ResponseEntity<List<CalendarEventResponse>> getEventsByStudent(@Valid @PathVariable Long studentId) {
        List<CalendarEventResponse> responses = calendarEventService.getAllEventsByStudent(studentId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<CalendarEventResponse>> getEventsByLesson(@Valid @PathVariable Long lessonId) {
        List<CalendarEventResponse> responses = calendarEventService.getEventsByLesson(lessonId);
        return ResponseEntity.ok(responses);
    }
    @GetMapping("/filter")
    public ResponseEntity<CalendarEventResponse> getFilteredEvents(
            @RequestParam(required = false) String day,
            @RequestParam(required = false) String week,
            @RequestParam(required = false) Boolean upcoming) {
        CalendarEventResponse events = (CalendarEventResponse) calendarEventService.getFilteredEvents(day, week, upcoming);
        return ResponseEntity.ok(events);
    }

}
