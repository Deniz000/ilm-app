package com.ilim.app.business.impl;

import com.ilim.app.business.validationhelper.LessonValidator;
import com.ilim.app.business.validationhelper.ValidationHelper;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dto.category.CategoryRequest;
import com.ilim.app.business.services.CategoryService;
import com.ilim.app.core.exceptions.EntityAlreadyExits;
import com.ilim.app.dataAccess.CategoryRepository;
import com.ilim.app.dto.category.CategoryResponse;
import com.ilim.app.dto.category.UpdateCategoryRequest;
import com.ilim.app.dto.lesson.LessonResponse;
import com.ilim.app.entities.Category;
import com.ilim.app.entities.Classroom;
import com.ilim.app.entities.Lesson;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ilim.app.core.util.EntityUpdateUtil.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapperService modelMapperService;
    private final ValidationHelper validationHelper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        log.error("Creating category: {}", request.getName());

        if (validationHelper.validateByName(Category.class, request.getName())) {
            throw new EntityAlreadyExits("Category with name " + request.getName() + " already exists");
        }
        Classroom classroom = validationHelper.getIfExistsById(Classroom.class, request.getClassroomId());
        Category category = modelMapperService.forRequest().map(request, Category.class);
        category.setClassroom(classroom);
        categoryRepository.save(category);
        log.error("Category created: {}", category.getName());
        return modelMapperService.forResponse().map(category, CategoryResponse.class);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        log.info("Fetching category by id {}", id);
        Category category = validationHelper.getIfExistsById(Category.class, id);
        return modelMapperService.forResponse().map(category, CategoryResponse.class);
    }

    @Override
    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
        log.info("Updating calendar event with ID: {}", id);
        Category category = validationHelper.getIfExistsById(Category.class, id);
        updateIfNotNull(category::setName, request.getName());
        updateIfNotNull(category::setDescription, request.getDescription());
        categoryRepository.save(category);
        log.info("Calendar event updated with ID: {}", id);
        return modelMapperService.forResponse().map(category, CategoryResponse.class);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        log.error("Fetching all categories");
        return categoryRepository.findAll().stream().map(category -> modelMapperService.forResponse().map(category, CategoryResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<LessonResponse> getLessonsByCategoryId(Long categoryId) {
        LessonValidator lessonValidator = validationHelper.getLessonsValidator();
        List<Lesson> lessons = lessonValidator.getLessonsByCategoryId(categoryId);
        return lessons.stream()
                .map(lesson -> modelMapperService.forResponse()
                        .map(lesson, LessonResponse.class))
                .collect(Collectors.toList());
    }


    @Override
    public void deleteCategory(Long id) {
        log.info("Deleting calendar event with ID: {}", id);
        Category category = validationHelper.getIfExistsById(Category.class, id);
        categoryRepository.deleteById(category.getId());
        log.info("Notification with ID: {} deleted.", id);

    }
}
