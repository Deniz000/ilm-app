package com.ilim.app.business.services;

import com.ilim.app.dto.attendance.AttendanceResponse;
import com.ilim.app.dto.attendance.CreateAttendanceRequest;

import java.util.List;

public interface AttendanceService {
    void markAttendance(CreateAttendanceRequest request);
    void deleteAttendance(Long id);
    List<AttendanceResponse> getAttendances(String type, Long id);
}
