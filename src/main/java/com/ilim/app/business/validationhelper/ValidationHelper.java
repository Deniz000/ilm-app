package com.ilim.app.business.validationhelper;

import com.ilim.app.entities.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValidationHelper {
    private final Map<Class<?>, Validator<?>> validators = new HashMap<>();

    public ValidationHelper(UserValidator userValidator,
                            ClassroomValidatorImpl classroomValidator,
                            CategoryValidator categoryValidator,
                            CalendarEventValidatorImpl calendarEventValidator,
                            NoteValidatorImpl noteValidator,
                            LessonValidatorImpl lessonValidator,
                            MaterialValidatorImpl materialValidator) {

        validators.put(UserEntity.class, userValidator);
        validators.put(Classroom.class, classroomValidator);
        validators.put(Category.class, categoryValidator);
        validators.put(CalendarEvent.class, calendarEventValidator);
        validators.put(Note.class, noteValidator);
        validators.put(Lesson.class, lessonValidator);
        validators.put(Material.class, materialValidator);
    }

    @SuppressWarnings("unchecked")
    public <T> T getIfExistsById(Class<T> type, Long id) {
        Validator<T> validator = (Validator<T>) validators.get(type);
        if (validator == null) {
            throw new IllegalArgumentException("No validator found for type: " + type.getName());
        }
        return validator.getIfExistsById(id);
    }
    @SuppressWarnings("unchecked")
    public <T> T getIfExistsByName(Class<T> type, String name) {
        Validator<T> validator = (Validator<T>) validators.get(type);
        if (validator == null) {
            throw new IllegalArgumentException("No validator found for type: " + type.getName());
        }
        return validator.getIfExistsByName(name);
    }
    @SuppressWarnings("unchecked")
    public boolean validateById(Class<?> type, Long id) {
        Validator<?> validator = validators.get(type);
        if (validator == null) {
            throw new IllegalArgumentException("No validator found for type: " + type.getName());
        }
        return ((Validator<Object>) validator).validateById(id);
    }
    @SuppressWarnings("unchecked")
    public boolean validateByName(Class<?> type, String name) {
        Validator<?> validator = validators.get(type);
        if (validator == null) {
            throw new IllegalArgumentException("No validator found for type: " + type.getName());
        }
        return ((Validator<Object>) validator).validateByName(name);
    }

    //special methods

    public MaterialValidator getMaterialsValidator() {
        return (MaterialValidator) validators.get(Material.class);
    }

    public NoteValidator getNotesValidator() {
        return (NoteValidator) validators.get(Note.class);
    }
    public CalendarEventValidator getEventsValidator() {
        return (CalendarEventValidator) validators.get(CalendarEvent.class);
    }
    public LessonValidator getLessonsValidator() {
        return (LessonValidator) validators.get(Lesson.class);
    }
    public ClassroomValidator getClassroomValidator() {
        return (ClassroomValidator) validators.get(Classroom.class);
    }
}
