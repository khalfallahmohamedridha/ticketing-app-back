package com.dksoft.tn.controller;

import com.dksoft.tn.dto.CategoryDto;
import com.dksoft.tn.exception.CategoryNotFoundException;
import com.dksoft.tn.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
//@CrossOrigin("*")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/save")
    public CategoryDto save(@RequestBody CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @PutMapping("/update/{id}")
    public CategoryDto update(@PathVariable Long id, @RequestBody CategoryDto categoryDto) throws CategoryNotFoundException {
        return categoryService.update(id, categoryDto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.deleteById(id);
    }


    /*@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")*/
    @GetMapping("/all")
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/get/{id}")
    public CategoryDto get(@PathVariable Long id) throws CategoryNotFoundException {
        return categoryService.getCategoryById(id);
    }
}
