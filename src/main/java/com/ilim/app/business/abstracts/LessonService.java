package com.ilim.app.business.abstracts;

import com.ilim.app.entities.Lesson;

import java.util.List;

public interface LessonService {
    Lesson createLesson(Lesson lesson);
    Lesson getLessonById(Long id);
    Lesson updateLesson(Long id, Lesson lessonDetails);
    void deleteLesson(Long id);
    List<Lesson> getAllLessons();
}
