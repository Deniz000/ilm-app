package com.ilim.app.business.services;

import com.ilim.app.dto.calendar.CalendarEventResponse;
import com.ilim.app.dto.lesson.LessonRequest;
import com.ilim.app.dto.lesson.LessonResponse;
import com.ilim.app.dto.lesson.LessonUpdateRequest;
import com.ilim.app.dto.material.MaterialResponse;
import com.ilim.app.dto.note.NoteResponse;

import java.util.List;

public interface LessonService {
    LessonResponse createLesson(LessonRequest lessonRequest);
    LessonResponse getLessonById(Long id);
    LessonResponse updateLesson(Long id, LessonUpdateRequest lessonRequest);
    void deleteLesson(Long id);
    List<LessonResponse> getAllLessons();
    List<LessonResponse> getLessonsForUser(Long userId);
    List<MaterialResponse> getMaterialsByLessonId(Long lessonId);

    List<NoteResponse> getNotesByLessonId(Long lessonId);

    List<CalendarEventResponse> getCalendarByLessonId(Long lessonId);
}
