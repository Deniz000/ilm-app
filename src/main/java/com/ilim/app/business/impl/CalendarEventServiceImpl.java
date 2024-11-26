package com.ilim.app.business.impl;

import com.ilim.app.dataAccess.CalendarEventRepository;
import com.ilim.app.dataAccess.LessonRepository;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.entities.CalendarEvent;
import com.ilim.app.entities.Lesson;
import com.ilim.app.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarEventServiceImpl extends CalendarEvent{

    private final CalendarEventRepository calendarEventRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    public CalendarEvent createCalendarEvent(Long creatorId, String title, String description,
                                             LocalDateTime startTime, LocalDateTime endTime,
                                             CalendarEvent.EventType eventType, Long lessonId) {
        UserEntity creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException("Creator not found"));

        Lesson lesson = null;
        if (eventType == CalendarEvent.EventType.LESSON && lessonId != null) {
            lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
        }
        CalendarEvent event = new CalendarEvent();
        event.setTitle(title);
        event.setDescription(description);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        event.setEventType(eventType);
        event.setLesson(lesson);
        event.setCreator(creator);

        return calendarEventRepository.save(event);
    }

    public List<CalendarEvent> getEventsByUser(Long userId) {
        return calendarEventRepository.findByCreatorId(userId);
    }

    public List<CalendarEvent> getEventsByLesson(Long lessonId) {
        return calendarEventRepository.findByLessonId(lessonId);
    }
}
