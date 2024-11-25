package com.ilim.app.business.impl;

import com.ilim.app.business.dto.material.MaterialRequest;
import com.ilim.app.business.dto.material.MaterialResponse;
import com.ilim.app.business.services.MaterialService;
import com.ilim.app.core.exceptions.EntityAlreadyExits;
import com.ilim.app.core.exceptions.MaterialNotFoundException;
import com.ilim.app.dataAccess.MaterialRepository;
import com.ilim.app.entities.Material;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialValidationHelper validationHelper;

    @Override
    public MaterialResponse addMaterial(MaterialRequest request) {
        if(validationHelper.checkIfMaterialExists(request.getTitle())) {
            throw new EntityAlreadyExits("Material already exists" + request.getTitle());
        }
        Material material = mapToEntity(request);
        return mapToResponse(materialRepository.save(material));
    }

    @Override
    public MaterialResponse getMaterialById(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException("Material not found with ID: " + id));
        return mapToResponse(material);
    }

    @Override
    public MaterialResponse updateMaterial(Long id, MaterialRequest response) {
        Material material = validationHelper.getIfMaterialExists(id);
        material.setTitle(response.getTitle());
        material.setFileUrl(response.getFileUrl());
        material.setLesson(validationHelper.getIfLessonExist(response.getLessonId()));
        return mapToResponse(materialRepository.save(material));
    }

    @Override
    public void deleteMaterial(Long id) {
        if(!materialRepository.existsById(id)) {
            throw new MaterialNotFoundException("Material not found with ID: " + id);
        }
        materialRepository.deleteById(id);
    }

    @Override
    public List<MaterialResponse> getMaterialsByLessonId(Long lessonId) {
        return materialRepository.findByLessonId(lessonId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private MaterialResponse mapToResponse(Material material) {
        return new MaterialResponse(material.getLesson().getTitle(), material.getTitle(), material.getFileUrl());
    }

    private Material mapToEntity(MaterialRequest materialRequest) {
        Material material = new Material();
        material.setTitle(materialRequest.getTitle());
        material.setFileUrl(materialRequest.getFileUrl());
        material.setLesson(validationHelper.getIfLessonExist(materialRequest.getLessonId()));
        return material;
    }
}
