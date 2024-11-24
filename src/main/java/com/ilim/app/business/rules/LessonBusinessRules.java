package com.ilim.app.business.rules;

import com.ilim.app.core.exceptions.BusinessExceptions;
import com.ilim.app.dataAccess.ClassroomRepository;
import com.ilim.app.dataAccess.LessonRepository;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.entities.Classroom;
import com.ilim.app.entities.Lesson;
import com.ilim.app.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LessonBusinessRules {
    private UserRepository userRepository;
    private ClassroomRepository classroomRepository;
    private LessonRepository lessonRepository;

    public UserEntity getUserIfExists(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessExceptions("Caller not found"));
    }

    public Classroom getIfClassroomExists(Long classroomId) {
        return classroomRepository.findById(classroomId)
                .orElseThrow(()-> new BusinessExceptions("Caller not found"));
    }

    public Lesson getIfLessonExists(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(()-> new BusinessExceptions("Caller not found"));
    }
}
