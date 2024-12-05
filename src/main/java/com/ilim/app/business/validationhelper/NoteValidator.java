package com.ilim.app.business.validationhelper;

import com.ilim.app.entities.Note;

import java.util.List;

public interface NoteValidator extends Validator<Note> {
    List<Note> getNotesByLessonId(Long lessonId);
    List<Note> getNotesByUserId(Long lessonId);

}
