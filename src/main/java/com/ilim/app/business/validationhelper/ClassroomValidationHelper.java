package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.ClassroomNotFoundException;
import com.ilim.app.core.exceptions.UserNotFoundException;
import com.ilim.app.dataAccess.ClassroomRepository;
import com.ilim.app.dataAccess.ClassroomUserRepository;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.entities.Classroom;
import com.ilim.app.entities.ClassroomUser;
import com.ilim.app.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record ClassroomValidationHelper(
        ClassroomRepository classroomRepository,
        UserRepository userRepository,
        ClassroomUserRepository classroomUserRepository
) {
    public UserEntity getUserIfExists(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Teacher not found"));
    }

    public Classroom getClassroomByClassCodeIfExist(String code) {
        return classroomRepository.findByClassCode(code)
                .orElseThrow(() -> new ClassroomNotFoundException("Classroom not found"));
    }

    public Classroom getClassroomBydIfExist(Long id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new ClassroomNotFoundException("Classroom not found"));
    }

    public boolean validateClassroomUserIfExists(Long studentId, Long classroomId) {
        return classroomUserRepository.existsByStudentIdAndClassroomId(studentId, classroomId);
    }

    public void saveClassUserRepository(ClassroomUser classroomUser) {
        classroomUserRepository.save(classroomUser);
    }

    public List<ClassroomUser> findClassroomUsersByStudentId(Long classromId) {
        return classroomUserRepository.findAllByClassroomId(classromId);
    }

}