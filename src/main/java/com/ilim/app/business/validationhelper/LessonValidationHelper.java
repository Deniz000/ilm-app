package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.CategoryNotFoundException;
import com.ilim.app.core.exceptions.ClassroomNotFoundException;
import com.ilim.app.core.exceptions.LessonNotFoundException;
import com.ilim.app.core.exceptions.UserNotFoundException;
import com.ilim.app.dataAccess.CategoryRepository;
import com.ilim.app.dataAccess.ClassroomRepository;
import com.ilim.app.dataAccess.LessonRepository;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.entities.Category;
import com.ilim.app.entities.Classroom;
import com.ilim.app.entities.Lesson;
import com.ilim.app.entities.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public record LessonValidationHelper(UserRepository userRepository, ClassroomRepository classroomRepository,
                                     LessonRepository lessonRepository, CategoryRepository categoryRepository) {

    public boolean validateLessonByTitle(String title) {
        return lessonRepository.existsByTitle(title);
    }

    public UserEntity getUserIfExists(Long userId) {
        log.error("geldi");
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Caller not found"));
    }

    public Classroom getClassroomIfExists(Long classroomId) {
        return classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ClassroomNotFoundException("Classroom not found"));
    }

    public Lesson getLessonIfExists(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found"));
    }

    public Category getCategoryIfExists(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

}
