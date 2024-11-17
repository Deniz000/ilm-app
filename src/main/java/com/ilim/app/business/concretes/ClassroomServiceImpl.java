package com.ilim.app.business.concretes;

import com.ilim.app.business.abstracts.ClassroomService;
import com.ilim.app.dataAccess.ClassroomRepository;
import com.ilim.app.entities.Classroom;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Override
    public Classroom createClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    @Override
    public Classroom getClassroomById(Long id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classroom not found with ID: " + id));
    }

    @Override
    public Classroom updateClassroom(Long id, Classroom classroomDetails) {
        Classroom classroom = getClassroomById(id);
        classroom.setName(classroomDetails.getName());
        classroom.setClassCode(classroomDetails.getClassCode());
        classroom.setUpdatedAt(LocalDateTime.now());
        return classroomRepository.save(classroom);
    }

    @Override
    public void deleteClassroom(Long id) {
        classroomRepository.deleteById(id);
    }

    @Override
    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }
}
