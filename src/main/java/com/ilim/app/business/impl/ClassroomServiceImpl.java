package com.ilim.app.business.impl;

import com.ilim.app.business.services.ClassroomService;
import com.ilim.app.dataAccess.ClassroomMembershipRepository;
import com.ilim.app.dataAccess.ClassroomRepository;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.entities.Classroom;
import com.ilim.app.entities.ClassroomMembership;
import com.ilim.app.entities.Role;
import com.ilim.app.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final ClassroomMembershipRepository classroomMembershipRepository;

    public Classroom createClassroom(Long teacherId, String classroomName) {
        UserEntity teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        if (teacher.getRoles().stream().noneMatch(role -> role.getName().equals(Role.RoleName.TEACHER))) {
            throw new IllegalArgumentException("Only teachers can create classrooms");
        }

        Classroom classroom = new Classroom();
        classroom.setName(classroomName);
        classroom.setCreatedBy(teacher);

        String classCode = generateUniqueClassCode();
        classroom.setClassCode(classCode);

        return classroomRepository.save(classroom);
    }

    private String generateUniqueClassCode() {
        // Benzersiz bir kod oluştur (örnek)
        String code;
        do {
            code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (classroomRepository.existsByClassCode(code));
        return code;
    }

    public void joinClassroom(Long studentId, String classCode) {
        // Classroom ve öğrenci doğrulama
        Classroom classroom = classroomRepository.findByClassCode(classCode)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));

        UserEntity student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        if (student.getRoles().stream().noneMatch(role -> role.getName().equals(Role.RoleName.STUDENT))) {
            throw new IllegalArgumentException("Only students can join classrooms");
        }

        boolean alreadyJoined = classroomMembershipRepository.existsByStudentIdAndClassroomId(student.getId(), classroom.getId());
        if (alreadyJoined) {
            throw new IllegalArgumentException("Student already joined this classroom");
        }

        ClassroomMembership membership = new ClassroomMembership();
        membership.setClassroom(classroom);
        membership.setStudent(student);
        classroomMembershipRepository.save(membership);
    }

    public List<UserEntity> listStudents(Long classroomId) {
        return classroomMembershipRepository.findByClassroomId(classroomId)
                .stream()
                .map(ClassroomMembership::getStudent)
                .collect(Collectors.toList());
    }
}
