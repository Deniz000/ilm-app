package com.ilim.app.business.validationhelper;

import com.ilim.app.entities.Lesson;

import java.util.List;

public interface LessonValidator extends Validator<Lesson> {
    List<Lesson> getLessonsByCategoryId(Long categoryId);
}
