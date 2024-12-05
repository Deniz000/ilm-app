package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.CalendarEventNotFoundException;
import com.ilim.app.core.exceptions.CategoryNotFoundException;
import com.ilim.app.dataAccess.CalendarEventRepository;
import com.ilim.app.entities.CalendarEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalendarEventValidatorImpl implements CalendarEventValidator {
    private final CalendarEventRepository repository;

    public CalendarEventValidatorImpl(CalendarEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean validateById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean validateByName(String name) {
        return repository.existsByTitle(name);
    }

    @Override
    public CalendarEvent getIfExistsById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id + " event not found"));
    }

    @Override
    public CalendarEvent getIfExistsByName(String name) {
        return repository.findCalendarEventByTitle(name)
                .orElseThrow(() -> new CategoryNotFoundException(name + " event not found"));
    }
    @Override
    public List<CalendarEvent> getCalendarEventsOfTeacher(Long teacherId) {
        return repository.findCalendarEventByCreatorId(teacherId)
                .orElseThrow(() -> new CalendarEventNotFoundException(teacherId + " has no any event"));
    }
    @Override
    public List<CalendarEvent> getCalendarEventsOfStudent(Long id) {
        return repository.findCalendarEventByParticipants_Id(id)
                .orElseThrow(() -> new CalendarEventNotFoundException(id + " has no any event"));
    }

    @Override
    public List<CalendarEvent> getCalendarEventsByLessonId(Long lessonId) {
        return repository.findCalendarEventByLesson_Id(lessonId)
                .orElseThrow(() -> new CalendarEventNotFoundException("There is no any event belonging to this lesson" + lessonId));
    }

}
