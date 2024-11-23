package com.ilim.app.business.abstracts;

import com.ilim.app.entities.Classroom;
import com.ilim.app.entities.UserEntity;

import java.util.List;

public interface ClassroomService {
    Classroom createClassroom(Long teacherId, String classroomName);
    void joinClassroom(Long studentId, String classCode);
    public List<UserEntity> listStudents(Long classroomId) ;
    }
