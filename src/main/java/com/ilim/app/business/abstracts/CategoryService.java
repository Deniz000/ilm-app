package com.ilim.app.business.abstracts;

import com.ilim.app.entities.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category getCategoryById(Long id);
    Category updateCategory(Long id, Category categoryDetails);
    void deleteCategory(Long id);
    List<Category> getAllCategories();
}
