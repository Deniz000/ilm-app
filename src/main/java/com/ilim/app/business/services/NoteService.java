package com.ilim.app.business.services;

import com.ilim.app.business.dto.note.NoteRequest;
import com.ilim.app.business.dto.note.NoteResponse;

import java.util.List;

public interface NoteService {
    NoteResponse createNote(NoteRequest request);
    NoteResponse getNoteById(Long id);
    NoteResponse updateNote(Long id, NoteRequest request);
    void deleteNote(Long id);
    List<NoteResponse> getNotesByLesson(Long lessonId);
    List<NoteResponse> getAllNotes(Long userId);
    }
