package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.CategoryNotFoundException;
import com.ilim.app.dataAccess.CategoryRepository;
import com.ilim.app.entities.Category;
import org.springframework.stereotype.Service;

@Service
public record CategoryValidationHelper(CategoryRepository categoryRepository) {
    public Category getCategoryIfExists(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
    }

    public Category getCategoryByNameIfExists(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with name: " + name));
    }


    public boolean validateCategoryExitsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public boolean validateCategoryExitsById(Long id) {
        return categoryRepository.existsById(id);
    }
}
