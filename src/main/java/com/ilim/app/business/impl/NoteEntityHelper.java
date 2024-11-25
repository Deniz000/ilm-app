package com.ilim.app.business.impl;

import com.ilim.app.core.exceptions.NoteNotFoundException;
import com.ilim.app.dataAccess.NoteRepository;
import com.ilim.app.entities.Note;
import org.springframework.stereotype.Service;

@Service
public record NoteEntityHelper
        (NoteRepository noteRepository) {
    public Note fetchNote(Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with ID: " + noteId));
    }

    public void checkIfNoteIdExists(Long noteId) {
        if (!noteRepository.existsById(noteId)) {
            throw new NoteNotFoundException("Note not found with ID: " + noteId);
        }
    }

}
