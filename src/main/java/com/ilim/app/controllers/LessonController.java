package com.ilim.app.controllers;

import com.ilim.app.business.services.LessonService;
import com.ilim.app.dto.lesson.LessonRequest;
import com.ilim.app.dto.lesson.LessonResponse;
import com.ilim.app.dto.lesson.LessonUpdateRequest;
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

    @PostMapping
    public ResponseEntity<LessonResponse> createLesson(@Valid @RequestBody LessonRequest lessonRequest) {
        return ResponseEntity.ok(lessonService.createLesson(lessonRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonResponse> getLessonById(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonResponse> updateLesson(@Valid @PathVariable Long id, @Valid  @RequestBody LessonUpdateRequest lessonRequest) {
        return ResponseEntity.ok(lessonService.updateLesson(id, lessonRequest));
    }

    @GetMapping
    public ResponseEntity<List<LessonResponse>> getAllLessons() {
        return ResponseEntity.ok(lessonService.getAllLessons());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@Valid @PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}
