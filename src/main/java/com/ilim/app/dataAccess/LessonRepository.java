package com.ilim.app.dataAccess;

import com.ilim.app.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    boolean existsByTitle(String title);
    List<Lesson> findLessonByClassroom_Id(Long classroomId);

//     @Query("SELECT l.materials FROM Lesson l WHERE l.id = :lessonId")
//     Set<Material> findMaterialsByLessonId(@Param("lessonId") Long lessonId);
//
//     @Query("SELECT l.notes FROM Lesson  l where l.id= :lessonId")
//     Set<Note> findNotesByLessonId(@Param("lessonId") Long lessonId);

    @Query("SELECT l FROM Lesson l WHERE l.caller.id = :callerId")
    List<Lesson> findLessonByCallerId(@Param("callerId") Long callerId);

    @Query("SELECT l FROM Lesson l JOIN l.participants p WHERE p.id = :userId")
    List<Lesson> findLessonsByUserId(@Param("userId") Long userId);

}
