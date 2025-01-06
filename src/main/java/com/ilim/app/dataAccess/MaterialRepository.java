package com.ilim.app.dataAccess;

import com.ilim.app.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    boolean existsMaterialByFileName(String fileName);
    Optional<List<Material>> findMaterialByLessonId(Long lessonId);

    Optional<Material> findMaterialByFileName(String fileName);
}
