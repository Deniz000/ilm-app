package com.ilim.app.business.services;

import com.ilim.app.dto.attendance.AttendanceResponse;
import com.ilim.app.dto.attendance.CreateAttendanceRequest;

import java.util.List;

public interface AttendanceService {
    void markAttendance(CreateAttendanceRequest request);
    void deleteAttendance(Long id);
    List<AttendanceResponse> getAttendancesByLesson(Long lessonId);
    List<AttendanceResponse> getAttendancesByUser(Long userId);
    List<AttendanceResponse> getAttendanceByEvent(Long eventId); // Hoca için bir etkinlikteki katılım listesi
}
