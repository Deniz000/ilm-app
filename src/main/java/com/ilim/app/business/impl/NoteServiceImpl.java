package com.ilim.app.business.impl;

import com.ilim.app.business.services.NoteService;
import com.ilim.app.business.validationhelper.NoteValidator;
import com.ilim.app.business.validationhelper.ValidationHelper;
import com.ilim.app.core.exceptions.NoteNotFoundException;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dataAccess.NoteRepository;
import com.ilim.app.dto.note.NoteRequest;
import com.ilim.app.dto.note.NoteResponse;
import com.ilim.app.entities.Lesson;
import com.ilim.app.entities.Note;
import com.ilim.app.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.ilim.app.core.util.EntityUpdateUtil.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final ModelMapperService modelMapperService;
    private final ValidationHelper validationHelper;

   // private final String getDate = new Formatter().getFormattedCallTime(LocalDateTime.now());

    @Override
    public NoteResponse createNote(NoteRequest request) {
        log.info("Creating note ");
        //saving same note is not a problem
        UserEntity creator = validationHelper.getIfExistsById(UserEntity.class, request.getCreatedBy());
        Lesson lesson = validationHelper.getIfExistsById(Lesson.class, request.getLessonId());
        Note note = new Note();
        note.setCreatedBy(creator);
        note.setLesson(lesson);
        modelMapperService.forRequest().map(request, note);
        noteRepository.save(note);
        log.info("Created note");
        return modelMapperService.forResponse().map(note, NoteResponse.class);
    }

    @Override
    public NoteResponse getNoteById(Long id) {
        log.info("Fetching notes: {}", id);
        Note note = validationHelper.getIfExistsById(Note.class, id);
        return modelMapperService.forResponse().map(note, NoteResponse.class);
    }

    @Override
    public NoteResponse updateNote(Long id, NoteRequest request) {
        log.info("Updating note with ID: {}", id);
        Note note = validationHelper.getIfExistsById(Note.class, id);
        updateIfNotNull(note::setTitle, request.getTitle());
        updateIfNotNull(note::setContent, request.getContent());
        updateIfNotNull(note::setUpdatedAt, request.getUpdatedAt());
        log.info("Note updated with ID: {}", id);
        return modelMapperService.forResponse().map(noteRepository.save(note), NoteResponse.class);
    }

    @Override
    public List<NoteResponse> getNotesByUser(Long userId) {
        log.info("Fetching notes for user ID: {}", userId);
        NoteValidator noteValidator = validationHelper.getNotesValidator();
        return noteValidator.getNotesByUserId(userId).stream()
                .map(note -> modelMapperService.forResponse().map(note, NoteResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<NoteResponse> getAll() {
        List<Note> notes = noteRepository.findAll();

        if (notes.isEmpty()) {
            throw new NoteNotFoundException("There is no any notes");
        }
        return notes.stream()
                .map(note -> modelMapperService.forResponse()
                        .map(note, NoteResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<NoteResponse> getNotesByLesson(Long lessonId) {
        log.info("Fetching notes for lesson ID: {}", lessonId);
        NoteValidator noteValidator = validationHelper.getNotesValidator();
        return noteValidator.getNotesByLessonId(lessonId).stream()
                .map(note -> modelMapperService.forResponse().map(note, NoteResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteNote(Long id) {
        log.info("Deleting note with ID: {}", id);
        Note note = validationHelper.getIfExistsById(Note.class, id);
        noteRepository.deleteById(note.getId());
        log.info("Note ID: {} deleted.", id);
    }
}
