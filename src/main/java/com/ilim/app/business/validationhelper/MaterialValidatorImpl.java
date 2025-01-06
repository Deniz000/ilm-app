package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.LessonNotFoundException;
import com.ilim.app.core.exceptions.MaterialNotFoundException;
import com.ilim.app.dataAccess.MaterialRepository;
import com.ilim.app.entities.Material;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaterialValidatorImpl implements MaterialValidator {
    private final MaterialRepository repository;

    public MaterialValidatorImpl(MaterialRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean validateById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean validateByName(String name) {
        return repository.existsMaterialByFileName(name);
    }

    @Override
    public Material getIfExistsById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException(id + " material not found"));
    }

    @Override
    public Material getIfExistsByName(String name) {
        return repository.findMaterialByFileName(name)
                .orElseThrow(() -> new MaterialNotFoundException(name + " material not found"));
    }

    public List<Material> getMaterialsByLessonId(Long lessonId) {
        return repository.findMaterialByLessonId(lessonId)
                .orElseThrow(() -> new MaterialNotFoundException("materials not found belonging to lesson " + lessonId));
    }
}
