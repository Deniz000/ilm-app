package com.ilim.app.business.abstracts;

import com.ilim.app.entities.Attendance;

import java.util.List;

public interface AttendanceService {
    Attendance createAttendance(Attendance attendance);
    Attendance getAttendanceById(Long id);
    Attendance updateAttendance(Long id, Attendance attendanceDetails);
    void deleteAttendance(Long id);
    List<Attendance> getAttendancesByLesson(Long lessonId);
    List<Attendance> getAttendancesByUser(Long userId);
}
