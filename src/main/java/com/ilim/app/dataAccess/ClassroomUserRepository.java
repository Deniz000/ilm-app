package com.ilim.app.dataAccess;

import com.ilim.app.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ClassroomUserRepository extends JpaRepository<ClassroomUser, Long> {

    boolean existsByStudentIdAndClassroomId(Long studentId, Long classroomId);
    Optional<ClassroomUser> findByStudentIdAndClassroomId(Long studentId, Long classroomId);
    List<ClassroomUser> findAllByClassroomId(Long classroomId);


}
