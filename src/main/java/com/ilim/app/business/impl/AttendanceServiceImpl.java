package com.ilim.app.business.impl;

import com.ilim.app.business.services.AttendanceService;
import com.ilim.app.business.validationhelper.AttendanceValidationHelper;
import com.ilim.app.core.Formatter;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dataAccess.AttendanceRepository;
import com.ilim.app.dto.attendance.AttendanceResponse;
import com.ilim.app.dto.attendance.CreateAttendanceRequest;
import com.ilim.app.dto.attendance.UpdateAttendanceRequest;
import com.ilim.app.entities.Attendance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ModelMapperService modelMapperService;
    private final AttendanceValidationHelper validate;


    @Override
    @Transactional
    public void createAttendance(CreateAttendanceRequest request) {
        validate.validateAttendanceRequest(request);
        log.info("Creating new Attendance: {}", request.getUserId());
        Attendance attendance = this.modelMapperService.forRequest().map(request, Attendance.class);
        attendance.setDate(new Formatter().getFormattedCallTime(LocalDateTime.now()));
        log.info("Attendance created: {}", attendance.getUser().getUsername());
        attendanceRepository.save(attendance);
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceResponse getAttendanceById(Long id) {
        log.info("Fetching Attendance with ID: {}", id);
        Attendance attendance = validate.getAttendanceIfExist(id);
        return this.modelMapperService.forResponse()
                .map(attendance, AttendanceResponse.class);
        // Kullanıcının yalnızca kendi katılımını görmesi için kontrol
        //  checkUserAuthorization(attendance);
    }

    @Override
    @Transactional
    public AttendanceResponse updateAttendance(Long id, UpdateAttendanceRequest request) {
        Attendance attendance = validate.getAttendanceIfExist(id);
        log.info("Updating Attendance with ID: {}", id);

        if (request.getStatus() != null) {
            try {
                attendance.setStatus(updateStatus(request.getStatus()));
            } catch (IllegalArgumentException e) {
                log.error("Invalid status value: {}", request.getStatus(), e);
                throw new IllegalArgumentException("Invalid status value provided.");
            }
        }
        attendanceRepository.save(attendance);
        log.info("Updated Attendance with ID: {}", id);
        return this.modelMapperService.forResponse().map(attendance, AttendanceResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponse> getAttendancesByLesson(Long lessonId) {
        log.info("Fetching Attendances for Lesson ID: {}", lessonId);
        return attendanceRepository.findByLessonId(lessonId).stream()
                .map(attendance -> this.modelMapperService.forResponse()
                        .map(attendance, AttendanceResponse.class))
                .toList();
    }

    @Override
    public List<AttendanceResponse> getAttendancesByUser(Long userId) {
        // Sadece kendi verilerini görmek isteyen kullanıcı için yetkilendirme kontrolü
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserEntity currentUser = (UserEntity) auth.getPrincipal();
//        if (!currentUser.getId().equals(userId) && !currentUser.getRole().equals(UserEntity.Role.ADMIN)) {
//            throw new SecurityException("Unauthorized access to other user's attendance records.");
//        }
        log.info("Fetching Attendances for User ID: {}", userId);
        return attendanceRepository.findByUserId(userId).stream()
                .map(attendance -> this.modelMapperService.forResponse()
                        .map(attendance, AttendanceResponse.class))
                .toList();
    }



    @Override
    @Transactional
    public void deleteAttendance(Long id) {
        log.info("Deleting Attendance with ID: {}", id);
        Attendance attendance = validate.getAttendanceIfExist(id);
        attendanceRepository.delete(attendance);
        log.info("Notification with ID: {} deleted.", id);
    }

//    private void checkUserAuthorization(Attendance attendance) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserEntity currentUser = (UserEntity) auth.getPrincipal();
//        if (!attendance.getUser().getId().equals(currentUser.getId()) && !currentUser.getRole().equals(UserEntity.Role.ADMIN)) {
//            throw new SecurityException("Unauthorized access to attendance.");
//        }
//    }


    private Attendance.AttendanceStatus updateStatus(String status) {
        return Attendance.AttendanceStatus.valueOf(status);
    }
}
