package com.ilim.app.dataAccess;

import com.ilim.app.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    boolean existsByTitle(String title);
    Optional<Lesson> findByTitle(String title);

    @Query("SELECT l FROM Lesson l WHERE l.caller.id = :callerId")
    List<Lesson> findLessonByCallerId(@Param("callerId") Long callerId);

    @Query("SELECT l FROM Lesson l JOIN l.participants p WHERE p.id = :userId")
    List<Lesson> findLessonsByUserId(@Param("userId") Long userId);

    @Query("SELECT l FROM Lesson l WHERE l.classroom.id = :classroomId")
    List<Lesson> findLessonsByClassroomId(@Param("classroomId") Long classroomId);

    Optional<List<Lesson>> findLessonsByCategoryId(Long categoryId);
}
