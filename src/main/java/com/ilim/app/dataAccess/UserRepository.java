package com.ilim.app.dataAccess;

import com.ilim.app.entities.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsById(@NotNull Long id);
    boolean existsByEmail(@NotNull String email);


    @Query(value = "SELECT COUNT(u.id) > 0 FROM users u " +
            "JOIN classroom_students cs ON u.id = cs.student_id " +
            "WHERE cs.classroom_id = :classroomId and cs.student_id= :studentId", nativeQuery = true)
    Long existsByClassroomId(@Param("classroomId") Long classroomId
                             , @Param("studentId") Long studentId);


    @Query(value = """
            SELECT u.id, u.username, u.email
            FROM users u
            JOIN user_roles ur ON u.id = ur.user_id
            JOIN roles r ON ur.role_id = r.id
            JOIN classroom_students cs ON u.id = cs.student_id
            WHERE r.name = 'STUDENT' AND cs.classroom_id = :classroomId
            """, nativeQuery = true)
    List<Object[]> fetchUsersWithRolesAndClassroom(@Param("classroomId") Long classroomId);


    Optional<UserEntity> findByEmail(String email);
}
