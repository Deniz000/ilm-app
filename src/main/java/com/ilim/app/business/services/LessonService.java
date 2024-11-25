package com.ilim.app.business.services;

import com.ilim.app.business.dto.lesson.LessonRequest;
import com.ilim.app.business.dto.lesson.LessonResponse;
import com.ilim.app.business.dto.lesson.LessonUpdateRequest;

import java.util.List;

public interface LessonService {
    LessonResponse createLesson(LessonRequest lessonRequest);
    LessonResponse getLessonById(Long id);
    LessonResponse updateLesson(Long id, LessonUpdateRequest lessonRequest);
    void deleteLesson(Long id);
    List<LessonResponse> getAllLessons();
    List<LessonResponse> getLessonsForUser(Long userId);
    List<LessonResponse> getLessonsForClassroom(Long classroomId);
    }
