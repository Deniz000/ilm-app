package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.AttendanceNotFoundException;
import com.ilim.app.dataAccess.AttendanceRepository;
import com.ilim.app.dto.attendance.CreateAttendanceRequest;
import com.ilim.app.entities.Attendance;
import org.springframework.stereotype.Component;

@Component
public record AttendanceValidationHelper(AttendanceRepository attendanceRepository) {

    public void validateAttendanceRequest(CreateAttendanceRequest request) {
        if (request.getLessonId() == null || request.getUserId() == null || request.getStatus() == null) {
            throw new IllegalArgumentException("Attendance must have valid Lesson, User, and Status.");
        }
    }
    public Attendance getAttendanceIfExist(Long attendanceId) {
        return attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance not found with ID: " + attendanceId));
    }

}
