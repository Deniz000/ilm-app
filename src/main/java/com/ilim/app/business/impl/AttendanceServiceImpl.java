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
import com.ilim.app.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

        Optional<Attendance> existingAttendance =
                attendanceRepository.findByUser_IdAndEvent_Id(user.getId(), event.getId());
        if (existingAttendance.isPresent()) {
            existingAttendance.get().setStatus(AttendanceStatus.fromString(request.getStatus()));
        } else {
            Attendance attendance = new Attendance();
            attendance.setEvent(event);
            attendance.setUser(user);
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
    public List<AttendanceResponse> getAttendancesByLesson(Long lessonId) {
        return getAttendances(validator -> validator.getAttendancesByLesson(lessonId));
    }

    @Override
    public List<AttendanceResponse> getAttendancesByUser(Long userId) {
        return getAttendances(validator -> validator.getAttendancesByUser(userId));
    }

    @Override
    public List<AttendanceResponse> getAttendanceByEvent(Long eventId) {
        return getAttendances(validator -> validator.getAttendanceByEvent(eventId));
    }

    private List<AttendanceResponse> getAttendances(Function<AttendanceValidator, List<Attendance>> fetchFunction) {
        AttendanceValidator attendanceValidator = validationHelper.getAttendanceValidator();
        List<Attendance> attendances = fetchFunction.apply(attendanceValidator); // Dinamik çağrı
        return attendances.stream()
                .map(attendance -> modelMapperService.forResponse().map(attendance, AttendanceResponse.class))
                .toList();
    }


}
