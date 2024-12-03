package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.ClassroomNotFoundException;
import com.ilim.app.dataAccess.ClassroomRepository;
import com.ilim.app.entities.Classroom;
import org.springframework.stereotype.Component;

@Component
public class ClassroomValidatorImpl implements ClassroomValidator {

    private final ClassroomRepository repository;

    public ClassroomValidatorImpl(ClassroomRepository classroomRepository) {
        this.repository = classroomRepository;
    }

    @Override
    public boolean validateById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean validateByName(String name) {
        return repository.existsClassroomByName(name);
    }

    @Override
    public Classroom getIfExistsById(Long id) {
        return repository.findById(id)
                .orElseThrow(()->new ClassroomNotFoundException("Classroom not found"));
    }

    @Override
    public Classroom getIfExistsByName(String name) {
        return repository.findClassroomByName(name)
                .orElseThrow(()->new ClassroomNotFoundException("Classroom not found"));
    }

    @Override
    public Classroom findClassroomByClassCode(String classCode) {
        return repository.findClassroomByClassCode(classCode)
                .orElseThrow(()->new ClassroomNotFoundException("There is no any classroom with this code"));
    }
}
