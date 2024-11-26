package com.ilim.app.business.impl;

import com.ilim.app.core.exceptions.ClassroomNotFoundException;
import com.ilim.app.core.exceptions.UserNotExistException;
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
                .orElseThrow(() -> new UserNotExistException("Teacher not found"));
    }
    public Classroom findClassroomByClassCode(String code) {
        return classroomRepository.findByClassCode(code)
                .orElseThrow(() -> new ClassroomNotFoundException("Classroom not found"));
    }
    public boolean getClassroomMembershipIfExists(Long studentId, Long classroomId) {
       return classroomUserRepository.existsByStudentIdAndClassroomId(studentId,classroomId);
    }
    public void saveClassUserRepository(ClassroomUser classroomUser) {
         classroomUserRepository.save(classroomUser);
    }
    public List<ClassroomUser> findClassroomUsersByStudentId(Long classromId) {
        return classroomUserRepository.findAllByClassroomId(classromId);
    }

}