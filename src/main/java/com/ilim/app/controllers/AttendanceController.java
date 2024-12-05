package com.ilim.app.controllers;

import com.ilim.app.business.services.AttendanceService;
import com.ilim.app.dto.attendance.AttendanceResponse;
import com.ilim.app.dto.attendance.CreateAttendanceRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    //tested -  05 //TODO maybe you can change request dto
    @PostMapping
    public ResponseEntity<Void> createAttendance(@Valid @RequestBody CreateAttendanceRequest request) {
        attendanceService.markAttendance(request);
        return ResponseEntity.ok().build();
    }

    //tested-05
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<AttendanceResponse>> getAttendancesByLesson(@PathVariable Long lessonId) {
        List<AttendanceResponse> responses = attendanceService.getAttendances("lesson", lessonId);
        return ResponseEntity.ok(responses);
    }

    //tested 05
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AttendanceResponse>> getAttendancesByUser(@PathVariable Long userId) {
        List<AttendanceResponse> responses = attendanceService.getAttendances("user", userId);
        return ResponseEntity.ok(responses);
    }


    //tested - 05
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByEvent(@PathVariable Long eventId) {
        List<AttendanceResponse> responses = attendanceService.getAttendances("event", eventId);
        return ResponseEntity.ok(responses);
    }

    //tested -05
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@Valid @PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}
