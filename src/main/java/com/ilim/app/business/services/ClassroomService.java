package com.ilim.app.business.services;

import com.ilim.app.dto.classroom.ClassroomRequest;
import com.ilim.app.dto.classroom.ClassroomResponse;
import com.ilim.app.dto.classroom.JoinClassroomRequest;
import com.ilim.app.entities.Classroom;
import com.ilim.app.entities.UserEntity;

import java.util.List;

public interface ClassroomService {
    ClassroomResponse createClassroom(Long teacherId, ClassroomRequest request);
    void joinClassroom(JoinClassroomRequest request);
    public List<UserEntity> listStudents(Long classroomId) ;
    }
