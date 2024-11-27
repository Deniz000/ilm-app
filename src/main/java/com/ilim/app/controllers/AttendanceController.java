package com.ilim.app.controllers;

import com.ilim.app.business.services.AttendanceService;
import com.ilim.app.dto.attendance.AttendanceResponse;
import com.ilim.app.dto.attendance.CreateAttendanceRequest;
import com.ilim.app.dto.attendance.UpdateAttendanceRequest;
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

    @PostMapping
    public ResponseEntity<Void> createAttendance(@Valid @RequestBody CreateAttendanceRequest request) {
        attendanceService.createAttendance(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceResponse> getAttendanceById(@Valid @PathVariable Long id) {
        AttendanceResponse response = attendanceService.getAttendanceById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceResponse> updateAttendance(
            @PathVariable Long id,
            @RequestBody UpdateAttendanceRequest request) {
        AttendanceResponse response = attendanceService.updateAttendance(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<AttendanceResponse>> getAttendancesByLesson(@Valid @PathVariable Long lessonId) {
        List<AttendanceResponse> responses = attendanceService.getAttendancesByLesson(lessonId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AttendanceResponse>> getAttendancesByUser(@Valid @PathVariable Long userId) {
        List<AttendanceResponse> responses = attendanceService.getAttendancesByUser(userId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@Valid @PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}
