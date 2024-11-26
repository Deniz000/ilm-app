package com.ilim.app.business.impl;

import com.ilim.app.business.validationhelper.CategoryValidationHelper;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dto.category.CategoryRequest;
import com.ilim.app.business.services.CategoryService;
import com.ilim.app.core.exceptions.CategoryNotFoundException;
import com.ilim.app.core.exceptions.EntityAlreadyExits;
import com.ilim.app.dataAccess.CategoryRepository;
import com.ilim.app.dto.category.CategoryResponse;
import com.ilim.app.entities.Category;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryValidationHelper validationHelper;
    private final ModelMapperService modelMapperService;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        log.info("Creating category: {}", request.getName());
        if(validationHelper.validateCategoryExitsByName(request.getName())){
            throw new EntityAlreadyExits("Category with name " + request.getName() + " already exists");
        }
        Category category = modelMapperService.forRequest().map(request, Category.class);
        categoryRepository.save(category);
        log.info("Category created: {}", category.getName());
        return modelMapperService.forResponse().map(category, CategoryResponse.class);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        log.info("Fetching category by id {}", id);
        Category category = validationHelper.getCategoryIfExists(id);
        return modelMapperService.forResponse().map(category, CategoryResponse.class);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        log.info("Updating calendar event with ID: {}", id);
        Category category = validationHelper.getCategoryIfExists(id);
        modelMapperService.forRequest().map(request, category);
        log.info("Calendar event updated with ID: {}", id);
        return modelMapperService.forResponse().map(category, CategoryResponse.class);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        log.info("Fetching all categories");
        return categoryRepository.findAll()
                .stream()
                .map(category -> modelMapperService.forResponse().map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }


    @Override
    public void deleteCategory(Long id) {
        log.info("Deleting calendar event with ID: {}", id);
        Category category = validationHelper.getCategoryIfExists(id);
        categoryRepository.deleteById(category.getId());
        log.info("Notification with ID: {} deleted.", id);

    }
}
