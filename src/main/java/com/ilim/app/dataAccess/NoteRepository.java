package com.ilim.app.dataAccess;

import com.ilim.app.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<List<Note>> findNoteByLessonId(Long lessonId);
    Optional<List<Note>> findNoteByCreatedById(Long id);

    boolean existsByTitle(String title);

    Optional<Note> findNoteByTitle(String title);
}
