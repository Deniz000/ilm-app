package com.ilim.app.business.impl;

import com.ilim.app.business.dto.category.CategoryRequest;
import com.ilim.app.business.services.CategoryService;
import com.ilim.app.core.exceptions.CategoryNotFoundException;
import com.ilim.app.core.exceptions.EntityAlreadyExits;
import com.ilim.app.dataAccess.CategoryRepository;
import com.ilim.app.business.dto.category.CategoryResponse;
import com.ilim.app.entities.Category;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryEntityHelper categoryEntityHelper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        if(categoryEntityHelper.checkIfCategoryExitsByName(request.getName())){
            throw new EntityAlreadyExits("Category with name " + request.getName() + " already exists");
        }
        Category category = mapToEntity(request);
        return mapToResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryEntityHelper.getIfCategoryExists(id);
        return mapToResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryEntityHelper.getIfCategoryExists(id);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return mapToResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        if(!categoryEntityHelper.checkIfCategoryExitsById(id)){
            throw new CategoryNotFoundException("Category does not exist" + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(category.getName(), category.getDescription());
    }

    private Category mapToEntity(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        return category;
    }
}
