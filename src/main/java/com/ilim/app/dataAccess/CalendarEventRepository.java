package com.ilim.app.dataAccess;

import com.ilim.app.entities.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    boolean existsByStartTime(LocalDateTime startTime);
    boolean existsByTitle(String title);
    Optional<List<CalendarEvent>> findCalendarEventByCreatorId(Long userId);
    Optional<List<CalendarEvent>> findCalendarEventByParticipants_Id(Long id);
    Optional<List<CalendarEvent>> findCalendarEventByLesson_Id(Long lessonId);
    Optional<CalendarEvent> findCalendarEventByTitle(String title);

    @Query("SELECT e FROM CalendarEvent e WHERE e.startTime BETWEEN :startDate AND :endDate")
    List<CalendarEvent> findEventsBetweenDates(@Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);

    @Query("SELECT e FROM CalendarEvent e WHERE e.startTime = :specificDate")
    List<CalendarEvent> findEventsBySpecificDate(@Param("specificDate") LocalDate specificDate);
}
