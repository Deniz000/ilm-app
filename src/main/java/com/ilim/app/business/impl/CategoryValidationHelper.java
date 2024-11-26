package com.ilim.app.business.impl;

import com.ilim.app.core.exceptions.CategoryNotFoundException;
import com.ilim.app.dataAccess.CategoryRepository;
import com.ilim.app.entities.Category;
import org.springframework.stereotype.Service;

@Service
public record CategoryValidationHelper(CategoryRepository categoryRepository) {
    public Category getIfCategoryExists(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
    }
    public boolean checkIfCategoryExitsByName(String name) {
        return categoryRepository.existsByName(name);
    }
public boolean checkIfCategoryExitsById(Long id) {
    return categoryRepository.existsById(id);
}
}
