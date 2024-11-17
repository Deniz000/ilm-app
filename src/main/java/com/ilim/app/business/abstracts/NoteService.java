package com.ilim.app.business.abstracts;

import com.ilim.app.entities.Note;

import java.util.List;

public interface NoteService {
    Note createNote(Note note);
    Note getNoteById(Long id);
    Note updateNote(Long id, Note noteDetails);
    void deleteNote(Long id);
    List<Note> getNotesByUser(Long userId);
    List<Note> getNotesByLesson(Long lessonId);
}
