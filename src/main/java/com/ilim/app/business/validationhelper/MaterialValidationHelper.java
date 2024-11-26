package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.LessonNotFoundException;
import com.ilim.app.core.exceptions.MaterialNotFoundException;
import com.ilim.app.dataAccess.LessonRepository;
import com.ilim.app.dataAccess.MaterialRepository;
import com.ilim.app.entities.Lesson;
import com.ilim.app.entities.Material;
import org.springframework.stereotype.Service;

@Service
public record MaterialValidationHelper(MaterialRepository materialRepository,
                                       LessonRepository lessonRepository) {

    public boolean validateMaterialExists(String materialName) {
        return materialRepository.existsMaterialByTitle(materialName);
    }

    public Lesson getLessonIfExist(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found" + id));
    }

    public Material getMaterialIfExists(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException("Material not found" + id));
    }
}