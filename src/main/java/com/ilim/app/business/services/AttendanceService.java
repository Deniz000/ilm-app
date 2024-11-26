package com.ilim.app.business.services;

import com.ilim.app.dto.attendance.AttendanceResponse;
import com.ilim.app.dto.attendance.CreateAttendanceRequest;
import com.ilim.app.dto.attendance.UpdateAttendanceRequest;

import java.util.List;

public interface AttendanceService {
    void createAttendance(CreateAttendanceRequest request);
    AttendanceResponse getAttendanceById(Long id);
    AttendanceResponse updateAttendance(Long id, UpdateAttendanceRequest request);
    void deleteAttendance(Long id);
    List<AttendanceResponse> getAttendancesByLesson(Long lessonId);
    List<AttendanceResponse> getAttendancesByUser(Long userId);
}
