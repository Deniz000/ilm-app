package com.ilim.app.business.validationhelper;

import com.ilim.app.entities.Material;

import java.util.List;

public interface MaterialValidator extends Validator<Material> {
    List<Material> getMaterialsByLessonId(Long lessonId);

}
