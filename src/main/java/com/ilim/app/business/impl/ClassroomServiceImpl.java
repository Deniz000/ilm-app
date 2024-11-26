package com.ilim.app.business.impl;

import com.ilim.app.business.services.ClassroomService;
import com.ilim.app.core.exceptions.EntityAlreadyExits;
import com.ilim.app.core.exceptions.UserNotTeacherException;
import com.ilim.app.dataAccess.ClassroomRepository;
import com.ilim.app.dto.classroom.ClassroomRequest;
import com.ilim.app.dto.classroom.ClassroomResponse;
import com.ilim.app.dto.classroom.JoinClassroomRequest;
import com.ilim.app.entities.Classroom;
import com.ilim.app.entities.ClassroomUser;
import com.ilim.app.entities.Role;
import com.ilim.app.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final ClassroomValidationHelper validationHelper;

    public ClassroomResponse createClassroom(Long teacherId, ClassroomRequest request) {
        UserEntity teacher = validationHelper.getUserIfExists(teacherId);

        if (teacher.getRoles().stream().noneMatch(role -> role.getName().equals(Role.RoleName.TEACHER))) {
            throw new UserNotTeacherException("Only teachers can create classrooms");
        }
        Classroom classroom = mapToModel(request);
        String classCode = generateUniqueClassCode();
        classroom.setClassCode(classCode);
        return mapToResponse(classroomRepository.save(classroom));
    }

    public void joinClassroom(JoinClassroomRequest request) {
        Classroom classroom = validationHelper.findClassroomByClassCode(request.getClassCode());
        UserEntity student = validationHelper.getUserIfExists(request.getStudentId());

        if (student.getRoles().stream().noneMatch(role -> role.getName().equals(Role.RoleName.STUDENT))) {
            throw new IllegalArgumentException("Only students can join classrooms");
        }

        boolean alreadyJoined = validationHelper.getClassroomMembershipIfExists(student.getId(), classroom.getId());
        if (alreadyJoined) {
            throw new EntityAlreadyExits("Student already joined this classroom");
        }
        ClassroomUser membership = new ClassroomUser();
        membership.setClassroom(classroom);
        membership.setStudent(student);
        validationHelper.saveClassUserRepository(membership);
    }

    public List<UserEntity> listStudents(Long classroomId) {
        return validationHelper.findClassroomUsersByStudentId(classroomId)
                .stream()
                .map(ClassroomUser::getStudent)
                .collect(Collectors.toList());
    }

    private String generateUniqueClassCode() {
        String code;
        do {
            code = UUID.randomUUID().toString().substring(0, 8).toUpperCase(); // UUID'nin ilk 8 karakteri
        } while (classroomRepository.existsByClassCode(code));
        return code;
    }
    private ClassroomResponse mapToResponse(Classroom classroom) {
        return new ClassroomResponse(
                classroom.getCreatedBy().getUsername(),
                classroom.getName(),
                classroom.getClassCode(),
                classroom.getCreatedAt()
        );
    }
    private Classroom mapToModel(ClassroomRequest request) {
        Classroom classroom = new Classroom();
        classroom.setCreatedBy(validationHelper.getUserIfExists(request.getCreatedBy()));
        classroom.setName(request.getName());
        classroom.setCreatedAt(request.getCreatedAt());
        return classroom;
    }
}