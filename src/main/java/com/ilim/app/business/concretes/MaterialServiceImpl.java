package com.ilim.app.business.concretes;

import com.ilim.app.business.abstracts.MaterialService;
import com.ilim.app.dataAccess.MaterialRepository;
import com.ilim.app.entities.Material;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public Material addMaterial(Material material) {
        return materialRepository.save(material);
    }

    @Override
    public Material getMaterialById(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material not found with ID: " + id));
    }

    @Override
    public Material updateMaterial(Long id, Material materialDetails) {
        Material material = getMaterialById(id);
        material.setName(materialDetails.getName());
        material.setFileUrl(materialDetails.getFileUrl());
        return materialRepository.save(material);
    }

    @Override
    public void deleteMaterial(Long id) {
        materialRepository.deleteById(id);
    }

    @Override
    public List<Material> getMaterialsByLessonId(Long lessonId) {
        return materialRepository.findByLessonId(lessonId);
    }
}
