package com.ilim.app.dataAccess;

import com.ilim.app.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findNoteByLessonId(Long lessonId);
    List<Note> findNoteByUserId(Long userId);
}
