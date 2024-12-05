package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.NoteNotFoundException;
import com.ilim.app.dataAccess.NoteRepository;
import com.ilim.app.entities.Note;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteValidatorImpl implements NoteValidator {
    private final NoteRepository repository;

    public NoteValidatorImpl(NoteRepository noteRepository) {
        this.repository = noteRepository;
    }

    @Override
    public boolean validateById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean validateByName(String name) {
        return repository.existsByTitle(name);
    }

    @Override
    public Note getIfExistsById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with ID: " + id));
    }

    @Override
    public Note getIfExistsByName(String name) {
        return repository.findNoteByTitle(name)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with ID: " + name));
    }

    @Override
    public List<Note> getNotesByLessonId(Long lessonId) {
        return repository.findNoteByLessonId(lessonId)
                .orElseThrow(() -> new NoteNotFoundException("Notes not found belong to lesson " + lessonId));
    }
    @Override
    public List<Note> getNotesByUserId(Long lessonId) {
        return repository.findNoteByCreatedById(lessonId)
                .orElseThrow(() -> new NoteNotFoundException("Notes not found belong to lesson " + lessonId));
    }
}
