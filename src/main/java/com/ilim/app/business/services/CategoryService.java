package com.ilim.app.business.services;

import com.ilim.app.dto.category.CategoryRequest;
import com.ilim.app.dto.category.CategoryResponse;
import com.ilim.app.dto.category.UpdateCategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse getCategoryById(Long id);
    CategoryResponse updateCategory(Long id, UpdateCategoryRequest request);
    void deleteCategory(Long id);
    List<CategoryResponse> getAllCategories();
}
