package com.ilim.app.business.impl;

import com.ilim.app.business.services.ClassroomService;
import com.ilim.app.business.validationhelper.ClassroomValidator;
import com.ilim.app.business.validationhelper.ValidationHelper;
import com.ilim.app.core.exceptions.EntityAlreadyExits;
import com.ilim.app.core.exceptions.UserNotTeacherException;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dataAccess.ClassroomRepository;
import com.ilim.app.dto.classroom.CreateClassroomRequest;
import com.ilim.app.dto.classroom.ClassroomResponse;
import com.ilim.app.dto.classroom.JoinClassroomRequest;
import com.ilim.app.entities.Classroom;
import com.ilim.app.entities.Role;
import com.ilim.app.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final ModelMapperService modelMapper;
    private final ValidationHelper validationHelper;

    public ClassroomResponse createClassroom(CreateClassroomRequest request) {
        if (validationHelper.validateByName(Classroom.class, request.getName())) {
            throw new EntityAlreadyExits("Classroom already exists, please choose a unique name");
        }
        log.info("Creating Classroom");
        UserEntity teacher = validationHelper.getIfExistsById(UserEntity.class, request.getTeacherId());

        //pre anotasyonları ile halledilecek .
        if (teacher.getRoles().stream().noneMatch(role -> role.getName().equals(Role.RoleName.TEACHER))) {
            throw new UserNotTeacherException("Only teachers can create classrooms");
        }
        Classroom classroom = new Classroom();
        modelMapper.forRequest().map(request, classroom);
        classroom.setTeacher(teacher);
        String classCode = generateUniqueClassCode();
        classroom.setClassCode(classCode);
        classroomRepository.save(classroom);
        log.info("Classroom created");
        return modelMapper.forResponse().map(classroom, ClassroomResponse.class);
    }

    public void joinClassroom(JoinClassroomRequest request) {
        log.info("Joining classroom");
        ClassroomValidator classroomValidator = validationHelper.getClassroomValidator();
        Classroom classroom =  classroomValidator.findClassroomByClassCode(request.getClassCode());
        UserEntity student = validationHelper.getIfExistsById(UserEntity.class, request.getStudentId());

        if (student.getRoles().stream().noneMatch(role -> role.getName().equals(Role.RoleName.STUDENT))) {
            throw new IllegalArgumentException("Only students can join classrooms");
        }
//        boolean alreadyJoined = validationHelper.validateClassroomUserIfExists(student.getId(), classroom.getId());
//        if (alreadyJoined) {
//            throw new EntityAlreadyExits("Student already joined this classroom");
//        }
//        //join
//        ClassroomUser membership = new ClassroomUser();
//        membership.setClassroom(classroom);
//        membership.setStudent(student);
//        log.info("Joined classroom");
//        validationHelper.saveClassUserRepository(membership);
    }

    public List<UserEntity> listStudents(Long classroomId) {
        log.info("All students is showed");
//        return validationHelper.findClassroomUsersByStudentId(classroomId)
//                .stream()
//                .map(ClassroomUser::getStudent)
//                .collect(Collectors.toList());
        return List.of( new UserEntity());
    }

    @Override
    public void deleteClassroom(Long id) {
        log.info("Deleting calendar event with ID: {}", id);
        Classroom classroom = validationHelper.getIfExistsById(Classroom.class, id);
        classroomRepository.delete(classroom);
        log.info("Notification with ID: {} deleted.", id);

    }

    private String generateUniqueClassCode() {
        log.info("Generating unique class code");
        String code;
        do {
            code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (classroomRepository.existsByClassCode(code));
        log.info("Generated unique class code: {}", code);
        return code;
    }
}