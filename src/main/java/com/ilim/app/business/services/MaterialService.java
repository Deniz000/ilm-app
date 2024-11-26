package com.ilim.app.business.services;

import com.ilim.app.dto.material.MaterialRequest;
import com.ilim.app.dto.material.MaterialResponse;

import java.util.List;

public interface MaterialService {
    MaterialResponse addMaterial(MaterialRequest request);
    MaterialResponse getMaterialById(Long id);
    MaterialResponse updateMaterial(Long id, MaterialRequest response);
    void deleteMaterial(Long id);
    List<MaterialResponse> getMaterialsByLessonId(Long lessonId);
}
