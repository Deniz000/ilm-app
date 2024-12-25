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

    //tested - 03
    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest request) {
        NoteResponse response = noteService.createNote(request);
        return ResponseEntity.ok(response);
    }

    //tested-05
    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        List<NoteResponse> response = noteService.getAll();
        return ResponseEntity.ok(response);
    }

    //tested-05
    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id) {
        NoteResponse response = noteService.getNoteById(id);
        return ResponseEntity.ok(response);
    }

    //tested -05
    @PutMapping
    public ResponseEntity<NoteResponse> updateNote(@Valid @RequestBody NoteRequest request) {
        NoteResponse response = noteService.updateNote(request.getId(), request);
        return ResponseEntity.ok(response);
    }

    //tested-05
    @GetMapping("/{userId}/notes")
    public ResponseEntity<List<NoteResponse>> getNotesByUser(@PathVariable Long userId) {
        List<NoteResponse> responses = noteService.getNotesByUser(userId);
        return ResponseEntity.ok(responses);
    }
    //tested - 05
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
