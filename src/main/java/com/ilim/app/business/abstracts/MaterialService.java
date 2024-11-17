package com.ilim.app.business.abstracts;

import com.ilim.app.entities.Material;

import java.util.List;

public interface MaterialService {
    Material addMaterial(Material material);
    Material getMaterialById(Long id);
    Material updateMaterial(Long id, Material materialDetails);
    void deleteMaterial(Long id);
    List<Material> getMaterialsByLessonId(Long lessonId);
}
