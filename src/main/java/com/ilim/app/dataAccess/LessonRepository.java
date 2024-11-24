package com.ilim.app.dataAccess;

import com.ilim.app.entities.Classroom;
import com.ilim.app.entities.Lesson;
import com.ilim.app.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByParticipantsContains(UserEntity participants);
    List<Lesson> findByClassroom(Classroom classroom);
}
