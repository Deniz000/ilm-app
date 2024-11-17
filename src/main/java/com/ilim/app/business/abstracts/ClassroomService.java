package com.ilim.app.business.abstracts;

import com.ilim.app.entities.Classroom;

import java.util.List;

public interface ClassroomService {
    Classroom createClassroom(Classroom classroom);
    Classroom getClassroomById(Long id);
    Classroom updateClassroom(Long id, Classroom classroomDetails);
    void deleteClassroom(Long id);
    List<Classroom> getAllClassrooms();
}
