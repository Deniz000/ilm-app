package com.ilim.app.controllers;

import com.ilim.app.business.services.CategoryService;
import com.ilim.app.dto.category.CategoryRequest;
import com.ilim.app.dto.category.CategoryResponse;
import com.ilim.app.dto.category.UpdateCategoryRequest;
import com.ilim.app.dto.lesson.LessonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //tested - 03
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.createCategory(request));
    }

    //tested - 03
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    //tested - 03
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    //tested - 03
    @PutMapping
    public ResponseEntity<CategoryResponse> updateCategory(@Valid @RequestBody UpdateCategoryRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(request.getId(), request));
    }


    //tested - 03
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{categoryId}/lessons")
    public ResponseEntity<List<LessonResponse>> getLessonsByCategoryId(@PathVariable Long categoryId) {
        List<LessonResponse> lessons = categoryService.getLessonsByCategoryId(categoryId);
        return ResponseEntity.ok(lessons);
    }
}
