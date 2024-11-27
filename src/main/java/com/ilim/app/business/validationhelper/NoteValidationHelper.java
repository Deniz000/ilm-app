package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.NoteNotFoundException;
import com.ilim.app.dataAccess.NoteRepository;
import com.ilim.app.entities.Note;
import org.springframework.stereotype.Component;

@Component
public record NoteValidationHelper
        (NoteRepository noteRepository) {

    public Note getNoteIfExist(Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with ID: " + noteId));
    }

}
