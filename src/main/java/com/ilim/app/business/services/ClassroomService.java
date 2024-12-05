package com.ilim.app.business.services;

import com.ilim.app.core.util.result.ApiResponse;
import com.ilim.app.dto.classroom.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface ClassroomService {
    ClassroomResponse createClassroom(CreateClassroomRequest request);
    ApiResponse<Void> joinClassroom(JoinClassroomRequest request);
    List<StudentsResponse> getStudentsInClassroom(Long classroomId) ;
    void deleteClassroom(Long classroomId);

    List<ClassroomResponse> getAllClassrooms();

    ClassroomResponse getClassroomById(Long id);

    ClassroomResponse updateClassroom(@NotNull(message = "Classroom id cannot be null") Long id, UpdateClassroomRequest request);
}
