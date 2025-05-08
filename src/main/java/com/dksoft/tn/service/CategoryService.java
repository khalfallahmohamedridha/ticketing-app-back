package com.dksoft.tn.service;

import com.dksoft.tn.dto.CategoryDto;
import com.dksoft.tn.entity.Category;
import com.dksoft.tn.exception.CategoryNotFoundException;
import com.dksoft.tn.mapper.CategoryMapper;
import com.dksoft.tn.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public CategoryDto save(CategoryDto categoryDto) {
        Category category = mapper.fromCategoryDto(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return mapper.fromCategory(savedCategory);
    }

    public CategoryDto update(Long id, CategoryDto categoryDto) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id = '" + id + "' not found."));
        updateCategoryFields(category, categoryDto);
        Category updatedCategory = categoryRepository.save(category);
        log.info("Category updated successfully");
        return mapper.fromCategory(updatedCategory);
    }

    private void updateCategoryFields(Category category, CategoryDto categoryDto) {
        category.setName(categoryDto.name());
        category.setDescription(categoryDto.description());
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
        log.info("Category deleted successfully");
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(mapper::fromCategory)
                .toList();
    }

    public CategoryDto getCategoryById(Long id) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id = '" + id + "' not found."));
        return mapper.fromCategory(category);
    }
}
