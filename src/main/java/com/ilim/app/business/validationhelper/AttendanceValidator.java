package com.ilim.app.business.validationhelper;

import com.ilim.app.entities.Attendance;

import java.util.List;

public interface AttendanceValidator extends Validator<Attendance> {
    List<Attendance> getAttendancesByLesson(Long lessonId);
    List<Attendance> getAttendancesByUser(Long userId);
    List<Attendance> getAttendanceByEvent(Long eventId);

}
