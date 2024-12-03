package com.ilim.app.dataAccess;

import com.ilim.app.entities.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    List<CalendarEvent> findByCreatorId(Long userId);
    Optional<List<CalendarEvent>> findCalendarEventByLessonId(Long lessonId);

    boolean existsByTitle(String title);

    Optional<CalendarEvent> findCalendarEventByTitle(String title);
}
