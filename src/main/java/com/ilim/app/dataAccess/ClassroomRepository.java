package com.ilim.app.dataAccess;

import com.ilim.app.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    boolean existsByClassCode(String classCode);
    boolean existsClassroomByName(String name);

    Optional<Classroom> findClassroomByName(String name);
    Optional<Classroom> findClassroomByClassCode(String classCode);
}
