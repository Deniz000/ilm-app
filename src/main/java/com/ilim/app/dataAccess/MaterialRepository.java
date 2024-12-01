package com.ilim.app.dataAccess;

import com.ilim.app.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    boolean existsMaterialByTitle(String title);
    List<Material> findByLessonId(Long lessonId);


}
