package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.CalendarEventNotFoundException;
import com.ilim.app.core.exceptions.LessonNotFoundException;
import com.ilim.app.core.exceptions.UserNotFoundException;
import com.ilim.app.dataAccess.CalendarEventRepository;
import com.ilim.app.dataAccess.LessonRepository;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.dto.calendar.CreateCalendarEventRequest;
import com.ilim.app.dto.calendar.UpdateCalendarEventRequest;
import com.ilim.app.entities.CalendarEvent;
import com.ilim.app.entities.Lesson;
import com.ilim.app.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public record CalendarEventValidationHelper(
        UserRepository userRepository,
        LessonRepository lessonRepository,
        CalendarEventRepository eventRepository
) {
    //I know!! But Its okay and  I like it
    public void validateAndUpdateEvent(CalendarEvent event, UpdateCalendarEventRequest request) {
        if (request.getTitle() != null) {
            event.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            event.setDescription(request.getDescription());
        }
        if (request.getStartTime() != null && request.getEndTime() != null) {
            validateStartEndTime(request.getStartTime(), request.getEndTime());
            event.setStartTime(request.getStartTime());
            event.setEndTime(request.getEndTime());
        } else if (request.getStartTime() != null) {
            event.setStartTime(request.getStartTime());
        } else if (request.getEndTime() != null) {
            event.setEndTime(request.getEndTime());
        }
        if (request.getEventType() != null) {
            try {
                event.setEventType(CalendarEvent.EventType.valueOf(request.getEventType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid event type provided.");
            }
        }
    }

    public void validateUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User with ID " + userId + " does not exist.");
        }
    }

    public void validateLessonExists(Long lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new LessonNotFoundException("Lesson with ID " + lessonId + " does not exist.");
        }
    }

    public void validateEventRequest(CreateCalendarEventRequest request) {
        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new IllegalArgumentException("Start time cannot be after end time.");
        }
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new IllegalArgumentException("Event title cannot be empty.");
        }
    }

    public void validateStartEndTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time.");
        }
    }

    public CalendarEvent getEventIfExists(Long id) {
        return eventRepository.findById(id).orElseThrow(() ->
                new CalendarEventNotFoundException("Calendar event with ID " + id + " does not exist."));
    }

    public Lesson getLessonIfExists(Long lessonId) {
        return lessonRepository.findById(lessonId).orElseThrow(() ->
                new LessonNotFoundException("Lesson with ID " + lessonId + " does not exist."));
    }

    public UserEntity getUserIfExists(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with ID " + userId + " does not exist."));
    }
}
