package com.ilim.app.controllers;

import com.ilim.app.business.services.NoteService;
import com.ilim.app.dto.note.NoteRequest;
import com.ilim.app.dto.note.NoteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest request) {
        NoteResponse response = noteService.createNote(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@Valid @PathVariable Long id) {
        NoteResponse response = noteService.getNoteById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(@Valid @PathVariable Long id,@Valid @RequestBody NoteRequest request) {
        NoteResponse response = noteService.updateNote(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NoteResponse>> getNotesByUser(@Valid @PathVariable Long userId) {
        List<NoteResponse> responses = noteService.getNotesByUser(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<NoteResponse>> getNotesByLesson(@Valid @PathVariable Long lessonId) {
        List<NoteResponse> responses = noteService.getNotesByLesson(lessonId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@Valid @PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
