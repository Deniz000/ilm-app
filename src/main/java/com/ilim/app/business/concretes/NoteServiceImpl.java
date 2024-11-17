package com.ilim.app.business.concretes;

import com.ilim.app.business.abstracts.NoteService;
import com.ilim.app.dataAccess.NoteRepository;
import com.ilim.app.entities.Note;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note getNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found with ID: " + id));
    }

    @Override
    public Note updateNote(Long id, Note noteDetails) {
        Note note = getNoteById(id);
        note.setContent(noteDetails.getContent());
        return noteRepository.save(note);
    }

    @Override
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public List<Note> getNotesByUser(Long userId) {
        return noteRepository.findByUserId(userId);
    }

    @Override
    public List<Note> getNotesByLesson(Long lessonId) {
        return noteRepository.findByLessonId(lessonId);
    }
}
