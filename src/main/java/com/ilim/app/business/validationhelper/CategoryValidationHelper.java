package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.CategoryNotFoundException;
import com.ilim.app.core.exceptions.ClassroomNotFoundException;
import com.ilim.app.dataAccess.CategoryRepository;
import com.ilim.app.dataAccess.ClassroomRepository;
import com.ilim.app.entities.Category;
import com.ilim.app.entities.Classroom;
import org.springframework.stereotype.Component;

@Component
public record CategoryValidationHelper(CategoryRepository categoryRepository,
                                       ClassroomRepository classroomRepository) {

    public Classroom getCategory(Long id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new ClassroomNotFoundException("Classroom not found"));
    }
    public Category getCategoryIfExists(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
    }

    public Category getCategoryByNameIfExists(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with name: " + name));
    }

    public boolean validateCategoryByNameIfExists(String name) {
        return categoryRepository.existsByName(name);
    }

    public boolean validateCategoryByIdIfExists(Long id) {
        return categoryRepository.existsById(id);
    }
}
