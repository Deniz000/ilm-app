package com.ilim.app.business.services;

import com.ilim.app.dto.material.MaterialRequest;
import com.ilim.app.dto.material.MaterialResponse;

public interface MaterialService {
    MaterialResponse addMaterial(MaterialRequest request);
    MaterialResponse getMaterialById(Long id);
    MaterialResponse updateMaterial(MaterialRequest response);
    void deleteMaterial(Long id);
}
