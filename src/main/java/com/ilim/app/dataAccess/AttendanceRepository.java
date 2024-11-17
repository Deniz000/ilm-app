package com.ilim.app.dataAccess;

import com.ilim.app.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByLessonId(Long lessonId);
    List<Attendance> findByUserId(Long userId);
}
