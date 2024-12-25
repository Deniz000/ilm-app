package com.ilim.app.controllers;

import com.ilim.app.business.services.CalendarEventService;
import com.ilim.app.dto.calendar.CalendarEventResponse;
import com.ilim.app.dto.calendar.CreateCalendarEventRequest;
import com.ilim.app.dto.calendar.UpdateCalendarEventRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/calendar-events")
@RequiredArgsConstructor
public class CalendarEventController {

    private final CalendarEventService calendarEventService;

    //tested -05
    @PostMapping
    public ResponseEntity<CalendarEventResponse> createCalendarEvent(@RequestBody CreateCalendarEventRequest request) {
        CalendarEventResponse response = calendarEventService.createCalendarEvent(request);
        return ResponseEntity.ok(response);
    }

    //tested-05
    @PutMapping
    public ResponseEntity<CalendarEventResponse> updateCalendarEvent(
            @Valid @RequestBody UpdateCalendarEventRequest request) {
        CalendarEventResponse response = calendarEventService.updateCalendarEvent(request.getId(), request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalendarEvent(@PathVariable Long id) {
        calendarEventService.deleteCalendarEvent(id);
        return ResponseEntity.noContent().build();
    }

//      Burası user controller tamamlandıktan sonra pathler değiştirilerek yazılacak.
//    @GetMapping("/user/{teacherId}")
//    public ResponseEntity<List<CalendarEventResponse>> getEventsByTeacher(@PathVariable Long teacherId) {
//        List<CalendarEventResponse> responses = calendarEventService.getAllEventsByTeacher(teacherId);
//        return ResponseEntity.ok(responses);
//    }
//
//    @GetMapping("/user/{studentId}")
//    public ResponseEntity<List<CalendarEventResponse>> getEventsByStudent(@PathVariable Long studentId) {
//        List<CalendarEventResponse> responses = calendarEventService.getAllEventsByStudent(studentId);
//        return ResponseEntity.ok(responses);
//    }

    //cant tested
    @GetMapping("/filter")
    public ResponseEntity<CalendarEventResponse> getFilteredEvents(
            @RequestParam(required = false) String day,
            @RequestParam(required = false) String week,
            @RequestParam(required = false) Boolean upcoming) {
        CalendarEventResponse events = (CalendarEventResponse) calendarEventService.getFilteredEvents(day, week, upcoming);
        return ResponseEntity.ok(events);
    }

}
