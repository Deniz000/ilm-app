package com.ilim.app.business.concretes;

import com.ilim.app.business.abstracts.LessonService;
import com.ilim.app.business.rules.LessonBusinessRules;
import com.ilim.app.core.exceptions.UserNotTeacherException;
import com.ilim.app.dataAccess.ClassroomRepository;
import com.ilim.app.dataAccess.LessonRepository;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.entities.Classroom;
import com.ilim.app.entities.Lesson;
import com.ilim.app.entities.Role;
import com.ilim.app.entities.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class LessonServiceImpl {

    private final LessonRepository lessonRepository;
    private final LessonBusinessRules lessonBusinessRules;

    public Lesson createLesson(Long callerId, Long classroomId, String title, String content,
                               LocalDateTime callTime, int duration, String callLink) {
        UserEntity caller = fetchUser(callerId);
        Classroom classroom = fetchClassroom(classroomId);

        Lesson lesson = new Lesson();
        lesson.setCaller(caller);
        lesson.setClassroom(classroom);
        lesson.setTitle(title);
        lesson.setContent(content);
        lesson.setCallTime(callTime);
        lesson.setDuration(duration);
        lesson.setCallLink(callLink);

        return lessonRepository.save(lesson);
    }

    // 2. Kullanıcının katıldığı dersleri listeleme
    public List<Lesson> getLessonsForUser(Long userId) {
        UserEntity user = fetchUser(userId);
        return lessonRepository.findByParticipantsContains(user);
    }

    // 3. Classroom içindeki dersleri listeleme
    public List<Lesson> getLessonsForClassroom(Long classroomId) {
        Classroom classroom = fetchClassroom(classroomId);
        return lessonRepository.findByClassroom(classroom);
    }

    public Lesson assignAdditionalTeacher(Long lessonId, Long teacherId) {
        Lesson lesson = fetchLesson(lessonId);
        UserEntity teacher = fetchUser(teacherId);

        if (teacher.getRoles().stream().noneMatch(role -> role.getName().equals(Role.RoleName.TEACHER))) {
            throw new UserNotTeacherException("User is not a teacher");
        }

        // Öğretmeni dersin katılımcı listesine ekle
        lesson.getParticipants().add(teacher);
        return lessonRepository.save(lesson);
    }

    private UserEntity fetchUser(Long userId) {
        return lessonBusinessRules.getUserIfExists(userId);
    }

    private Classroom fetchClassroom(Long classroomId) {
        return lessonBusinessRules.getIfClassroomExists(classroomId);
    }
    private Lesson fetchLesson(Long lessonId) {
        return lessonBusinessRules.getIfLessonExists(lessonId);
    }
}

