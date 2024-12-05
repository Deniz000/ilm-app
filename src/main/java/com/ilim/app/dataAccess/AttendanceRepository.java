package com.ilim.app.dataAccess;

import com.ilim.app.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<List<Attendance>> findAttendanceByLesson_Id(Long lessonId);
    Optional<List<Attendance>> findByUserId(Long userId);
    Optional<List<Attendance>> findByEventId(Long eventId);

    Optional<Attendance> findByUser_IdAndEvent_Id(Long userId, Long eventId);

}
