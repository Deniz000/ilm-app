package com.ilim.app.business.impl;

import com.ilim.app.business.services.NoteService;
import com.ilim.app.dataAccess.NoteRepository;
import com.ilim.app.dto.note.NoteRequest;
import com.ilim.app.dto.note.NoteResponse;
import com.ilim.app.entities.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteValidationHelper validationHelper;

    @Override
    public NoteResponse createNote(NoteRequest request) {
        Note note = mapToEntity(request);
        LocalDateTime now = LocalDateTime.now();
        note.setCreatedAt(now);
        note.setUpdatedAt(now);
        return mapToResponse(noteRepository.save(note));
    }

    @Override
    public NoteResponse getNoteById(Long id) {
        return mapToResponse(validationHelper.fetchNote(id));
    }

    @Override
    public NoteResponse updateNote(Long id, NoteRequest request) {
        Note note = validationHelper.fetchNote(id);
        note.setUpdatedAt(LocalDateTime.now());
        note.setContent(request.getContent());
        return mapToResponse(noteRepository.save(note));
    }

    @Override
    public void deleteNote(Long id) {
        validationHelper.checkIfNoteIdExists(id);
        noteRepository.deleteById(id);
    }

    @Override
    public List<NoteResponse> getAllNotes(Long userId) {
        return noteRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<NoteResponse> getNotesByLesson(Long lessonId) {
        return noteRepository.findNoteByLessonId(lessonId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private NoteResponse mapToResponse(Note note) {
        return new NoteResponse(
                note.getUser().getId(),
                note.getLesson().getId(),
                note.getContent(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );

    }

    private Note mapToEntity(NoteRequest request) {
        Note note = new Note();
        note.setContent(request.getContent());
        return note;
    }
}
