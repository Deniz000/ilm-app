package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.LessonNotFoundException;
import com.ilim.app.core.exceptions.MaterialNotFoundException;
import com.ilim.app.dataAccess.AttendanceRepository;
import com.ilim.app.entities.Attendance;
import com.ilim.app.entities.CalendarEvent;
import org.springframework.stereotype.Component;

@Component
public class AttendanceValidator implements Validator<Attendance> {
    private final AttendanceRepository repository;

    public AttendanceValidator(AttendanceRepository repository) {
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
}
