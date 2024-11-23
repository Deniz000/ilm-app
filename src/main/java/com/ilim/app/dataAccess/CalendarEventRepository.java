package com.ilim.app.dataAccess;

import com.ilim.app.entities.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    List<CalendarEvent> findByCreatorId(Long userId);
    List<CalendarEvent> findByLessonId(Long lessonId);
}
