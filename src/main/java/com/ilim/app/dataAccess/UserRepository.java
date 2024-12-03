package com.ilim.app.dataAccess;

import com.ilim.app.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String name);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

//    @Query("SELECT u.username FROM UserEntity u JOIN u.lessons l WHERE l.id = :lessonId")
//    Set<String> findParticipantNamesByLessonId(@Param("lessonId") Long lessonId);

//    @Query(value = """
//                SELECT u.id AS id, u.email AS email, u.username AS username, u.password AS password,
//                       GROUP_CONCAT(r.name) AS roles
//                FROM users u
//                JOIN users_roles ur ON u.id = ur.user_id
//                JOIN roles r ON ur.role_id = r.id
//                GROUP BY u.id, u.email, u.username, u.password
//            """, nativeQuery = true)
//    List<UserWithRolesDTO> fetchUsersWithRoles();

//    @Query(value = """
//                SELECT
//                    u.id,
//                    u.email,
//                    u.username,
//                    u.password,
//                    GROUP_CONCAT(r.name) AS roles
//                FROM users u
//                JOIN user_roles ur ON u.id = ur.user_id
//                JOIN roles r ON ur.role_id = r.id
//                GROUP BY u.id, u.email, u.username, u.password
//            """, nativeQuery = true)
//    List<Object[]> fetchUsersWithRoles();

    Optional<UserEntity> findByEmail(String email);
}
