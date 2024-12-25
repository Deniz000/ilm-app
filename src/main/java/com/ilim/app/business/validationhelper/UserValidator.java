package com.ilim.app.business.validationhelper;

import com.ilim.app.dto.classroom.StudentsResponse;
import com.ilim.app.entities.UserEntity;

import java.util.List;

public interface UserValidator extends Validator<UserEntity> {
    List<StudentsResponse> getAllStudents(Long classroomId);
    boolean validateStudentIfEnrolled(Long classroomId, Long studentId);
    boolean validateByEmail(String email);
}
