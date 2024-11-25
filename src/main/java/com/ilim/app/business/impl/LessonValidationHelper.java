package com.ilim.app.business.impl;

import com.ilim.app.core.exceptions.ClassroomNotFoundException;
import com.ilim.app.core.exceptions.LessonNotFoundException;
import com.ilim.app.core.exceptions.UserNotExistException;
import com.ilim.app.dataAccess.CategoryRepository;
import com.ilim.app.dataAccess.ClassroomRepository;
import com.ilim.app.dataAccess.LessonRepository;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.entities.Category;
import com.ilim.app.entities.Classroom;
import com.ilim.app.entities.Lesson;
import com.ilim.app.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public  record  LessonValidationHelper(UserRepository userRepository, ClassroomRepository classroomRepository,
LessonRepository lessonRepository, CategoryRepository categoryRepository) {

    public UserEntity getUserIfExists(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotExistException("Caller not found"));
    }

    public Classroom getIfClassroomExists(Long classroomId) {
        return classroomRepository.findById(classroomId)
                .orElseThrow(()-> new ClassroomNotFoundException("Caller not found"));
    }

    public Lesson getIfLessonExists(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(()-> new LessonNotFoundException("Caller not found"));
    }
    public boolean exitsLessonByTitle(String title) {
        return lessonRepository.existsByTitle(title);
    }

    public Category getIfCategoryExists(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ClassroomNotFoundException("Caller not found"));
    }

}
