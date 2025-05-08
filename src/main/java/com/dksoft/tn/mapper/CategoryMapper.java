package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.CategoryDto;
import com.dksoft.tn.entity.Category;
import lombok.NonNull;


public interface CategoryMapper {
    Category fromCategoryDto(@NonNull CategoryDto dto);
    CategoryDto fromCategory(Category category);
}
