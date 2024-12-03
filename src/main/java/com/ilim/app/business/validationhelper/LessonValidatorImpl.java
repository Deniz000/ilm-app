package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.LessonNotFoundException;
import com.ilim.app.dataAccess.LessonRepository;
import com.ilim.app.entities.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonValidatorImpl implements LessonValidator {
    private final LessonRepository repository;

    public LessonValidatorImpl(LessonRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean validateById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean validateByName(String name) {
        return repository.existsByTitle(name);
    }

    @Override
    public Lesson getIfExistsById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new LessonNotFoundException(id + " lesson not found"));
    }

    @Override
    public Lesson getIfExistsByName(String name) {
        return repository.findByTitle(name)
                .orElseThrow(() -> new LessonNotFoundException(name + " lesson title not found"));
    }

    @Override
    public List<Lesson> getLessonsByCategoryId(Long categoryId) {
        return repository.findLessonsByCategoryId(categoryId)
                .orElseThrow(() -> new LessonNotFoundException("There is no lesson belong to this category" + categoryId));
    }
}
