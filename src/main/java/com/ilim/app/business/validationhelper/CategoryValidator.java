package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.CategoryNotFoundException;
import com.ilim.app.dataAccess.CategoryRepository;
import com.ilim.app.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidator implements Validator<Category> {
    private final CategoryRepository repository;

    public CategoryValidator(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean validateById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean validateByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Category getIfExistsById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id+" category not found"));
    }

    @Override
    public Category getIfExistsByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException(name+" category not found"));
    }
}
