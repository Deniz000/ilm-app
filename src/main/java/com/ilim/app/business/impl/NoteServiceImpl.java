package com.ilim.app.business.impl;

import com.ilim.app.business.services.NoteService;
import com.ilim.app.business.validationhelper.ValidationHelper;
import com.ilim.app.core.Formatter;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dataAccess.NoteRepository;
import com.ilim.app.dto.note.NoteRequest;
import com.ilim.app.dto.note.NoteResponse;
import com.ilim.app.entities.Note;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final ModelMapperService modelMapperService;
    private final ValidationHelper validationHelper;

    private final String getDate = new Formatter().getFormattedCallTime(LocalDateTime.now());

    @Override
    public NoteResponse createNote(NoteRequest request) {
        log.info("Creating note ");
        //saving same note is not a problem
        Note note = modelMapperService.forRequest().map(request, Note.class);
        note.setCreatedAt(getDate);
        note.setUpdatedAt(getDate);
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
        note.setContent(request.getContent());
        note.setUpdatedAt(getDate);
        log.info("Note updated with ID: {}", id);
        return modelMapperService.forResponse().map(noteRepository.save(note), NoteResponse.class);
    }

    @Override
    public List<NoteResponse> getNotesByUser(Long userId) {
        log.info("Fetching notes for user ID: {}", userId);
        return noteRepository.findNoteByCreatedById(userId).stream()
                .map(note -> modelMapperService.forResponse().map(note, NoteResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<NoteResponse> getNotesByLesson(Long lessonId) {
        log.info("Fetching notes for lesson ID: {}", lessonId);
        return noteRepository.findNoteByLessonId(lessonId).stream()
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
