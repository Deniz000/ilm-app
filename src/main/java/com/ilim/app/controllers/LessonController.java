package com.ilim.app.controllers;

import com.ilim.app.business.services.LessonService;
import com.ilim.app.dto.calendar.CalendarEventResponse;
import com.ilim.app.dto.lesson.LessonRequest;
import com.ilim.app.dto.lesson.LessonResponse;
import com.ilim.app.dto.lesson.LessonUpdateRequest;
import com.ilim.app.dto.material.MaterialResponse;
import com.ilim.app.dto.note.NoteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    //tested - 03
    @PostMapping
    public ResponseEntity<LessonResponse> createLesson(@Valid @RequestBody LessonRequest lessonRequest) {
        return ResponseEntity.ok(lessonService.createLesson(lessonRequest));
    }

    //tested - 03
    // Dersin tüm bilgileri (detaylı bilgi)
    @GetMapping("/{id}")
    public ResponseEntity<LessonResponse> getLessonById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }

    //tested - 03
    @PutMapping
    public ResponseEntity<LessonResponse> updateLesson(@RequestBody LessonUpdateRequest request) {
        return ResponseEntity.ok(lessonService.updateLesson(request.getId(), request));
    }

    //tested - 03
    @GetMapping
    public ResponseEntity<List<LessonResponse>> getAllLessons() {
        return ResponseEntity.ok(lessonService.getAllLessons());
    }

    //tested -03
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }

    // Dersin materyalleri - tested - 03
    @GetMapping("/{lessonId}/materials")
    public ResponseEntity<List<MaterialResponse>> getMaterialsByLessonId(@PathVariable Long lessonId) {
        List<MaterialResponse> materials = lessonService.getMaterialsByLessonId(lessonId);
        return ResponseEntity.ok(materials);
    }

    // Dersin notları - tested - 03
    @GetMapping("/{lessonId}/notes")
    public ResponseEntity<List<NoteResponse>> getNotesByLessonId(@PathVariable Long lessonId) {
        List<NoteResponse> notes = lessonService.getNotesByLessonId(lessonId);
        return ResponseEntity.ok(notes);
    }

    // Dersin takvimi - tested -03
    @GetMapping("/{lessonId}/calendar")
    public ResponseEntity<List<CalendarEventResponse>> getCalendarByLessonId(@PathVariable Long lessonId) {
        List<CalendarEventResponse> calendar = lessonService.getCalendarByLessonId(lessonId);
        return ResponseEntity.ok(calendar);
    }

}
