package com.ilim.app.business.validationhelper;

import com.ilim.app.entities.Classroom;

public interface ClassroomValidator extends Validator<Classroom> {
    Classroom findClassroomByClassCode(String classCode);
}
