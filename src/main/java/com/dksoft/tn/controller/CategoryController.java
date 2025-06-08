package com.dksoft.tn.controller;

import com.dksoft.tn.dto.CategoryDto;
import com.dksoft.tn.entity.Category;
import com.dksoft.tn.exception.CategoryNotFoundException;
import com.dksoft.tn.mapper.CategoryMapper;
import com.dksoft.tn.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.fromCategoryDto(categoryDto);
        Category savedCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(categoryMapper.fromCategory(savedCategory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable long id) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        return ResponseEntity.ok(categoryMapper.fromCategory(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDto> categoryDtos = categories.stream()
                .map(categoryMapper::fromCategory)
                .toList();
        return ResponseEntity.ok(categoryDtos);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable long id, @Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.fromCategoryDto(categoryDto);
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(categoryMapper.fromCategory(updatedCategory));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}