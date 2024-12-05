package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.AttendanceNotFoundException;
import com.ilim.app.dataAccess.AttendanceRepository;
import com.ilim.app.entities.Attendance;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttendanceValidatorImpl implements AttendanceValidator {
    private final AttendanceRepository repository;

    public AttendanceValidatorImpl(AttendanceRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean validateById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean validateByName(String name) {
        return false;
    }

    @Override
    public Attendance getIfExistsById(Long id) {
        return null;
    }

    @Override
    public Attendance getIfExistsByName(String name) {
        return null;
    }

    @Override
    public List<Attendance> getAttendancesByLesson(Long lessonId) {
        return repository.findByLessonId(lessonId)
                .orElseThrow(() -> new AttendanceNotFoundException("There is no any attendance with this lesson" + lessonId));
    }

    @Override
    public List<Attendance> getAttendancesByUser(Long userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new AttendanceNotFoundException("There is no attendance with this user"+ userId));
    }

    @Override
    public List<Attendance> getAttendanceByEvent(Long eventId) {
        return repository.findByEventId(eventId)
                .orElseThrow(() -> new AttendanceNotFoundException("There is no attendance with this event"+ eventId));
    }
}
