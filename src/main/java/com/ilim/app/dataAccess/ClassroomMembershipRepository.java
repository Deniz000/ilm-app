package com.ilim.app.dataAccess;

import com.ilim.app.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ClassroomMembershipRepository extends JpaRepository<ClassroomMembership, Long> {

    boolean existsByStudentIdAndClassroomId(Long studentId, Long classroomId);
    List<ClassroomMembership> findByClassroomId(Long classroomId);
}
