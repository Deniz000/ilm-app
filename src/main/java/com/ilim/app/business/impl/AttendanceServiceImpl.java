package com.ilim.app.business.impl;

import com.ilim.app.business.services.AttendanceService;
import com.ilim.app.business.validationhelper.AttendanceValidator;
import com.ilim.app.business.validationhelper.ValidationHelper;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dataAccess.AttendanceRepository;
import com.ilim.app.dto.attendance.AttendanceResponse;
import com.ilim.app.dto.attendance.CreateAttendanceRequest;
import com.ilim.app.entities.Attendance;
import com.ilim.app.entities.Attendance.AttendanceStatus;
import com.ilim.app.entities.CalendarEvent;
import com.ilim.app.entities.Lesson;
import com.ilim.app.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ModelMapperService modelMapperService;
    private final ValidationHelper validationHelper;

    @Override
    @Transactional
    public void markAttendance(CreateAttendanceRequest request) {
        CalendarEvent event = validationHelper.getIfExistsById(CalendarEvent.class, request.getEventId());
        UserEntity user = validationHelper.getIfExistsById(UserEntity.class, request.getUserId());
        Lesson lesson = validationHelper.getIfExistsById(Lesson.class, request.getLessonId());
        Optional<Attendance> existingAttendance =
                attendanceRepository.findByUser_IdAndEvent_Id(user.getId(), event.getId());
        if (existingAttendance.isPresent()) {
            existingAttendance.get().setStatus(AttendanceStatus.fromString(request.getStatus()));
        } else {
            Attendance attendance = new Attendance();
            attendance.setEvent(event);
            attendance.setUser(user);
            attendance.setLesson(lesson);
            attendance.setStatus(AttendanceStatus.fromString(request.getStatus()));
            attendance.setAttendanceDate(event.getStartTime());
            attendanceRepository.save(attendance);
        }}

    @Override
    public void deleteAttendance(Long id) {
        log.info("Delete attendance: {}", id);
        Attendance attendance = validationHelper.getIfExistsById(Attendance.class, id);
        attendanceRepository.delete(attendance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponse> getAttendances(String type, Long id) {
        AttendanceValidator attendanceValidator = validationHelper.getAttendanceValidator();
        List<Attendance> attendances = switch (type.toUpperCase()) {
            case "LESSON" -> attendanceValidator.getAttendancesByLesson(id);
            case "USER" -> attendanceValidator.getAttendancesByUser(id);
            case "EVENT" -> attendanceValidator.getAttendanceByEvent(id);
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        };
        return attendances.stream()
                .map(attendance -> modelMapperService.forResponse().map(attendance, AttendanceResponse.class))
                .toList();
    }
}
