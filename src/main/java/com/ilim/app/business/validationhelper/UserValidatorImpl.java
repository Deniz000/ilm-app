package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.UserNotFoundException;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.dto.classroom.StudentsResponse;
import com.ilim.app.entities.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserValidatorImpl implements UserValidator {
    private final UserRepository userRepository;

    public UserValidatorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean validateById(Long id)  {
        return userRepository.existsById(id);
    }

    @Override
    public boolean validateByName(String name){return false;}


    @Override
    public boolean validateStudentIfEnrolled(Long classroomId, Long studentId) {
        Long areThere = userRepository.existsByClassroomId(classroomId, studentId);
        return areThere != null && areThere > 0;
    }

    @Override
    public boolean validateByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserEntity getIfExistsById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id + " not found -user"));
    }

    @Override
    public UserEntity getIfExistsByName(String name) {
        return null;
    }

    @Override
    public List<StudentsResponse> getAllStudents(Long classroomId) {
       List<Object[]> students =  userRepository.fetchUsersWithRolesAndClassroom(classroomId);
        return students.stream()
                .map(student -> StudentsResponse.builder()
                        .id((Long) student[0])
                        .username(student[1].toString())
                        .email(student[2].toString())
                        .build()
                ).toList();
    }
}
