package com.ilim.app.dataAccess;

import com.ilim.app.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String name);
    boolean existsById(Long userId);

    @Query("SELECT u.username FROM UserEntity u JOIN u.lessons l WHERE l.id = :lessonId")
    Set<String> findParticipantNamesByLessonId(@Param("lessonId") Long lessonId);

}
