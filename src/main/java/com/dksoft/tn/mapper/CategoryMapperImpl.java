package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.CategoryDto;
import com.dksoft.tn.entity.Category;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category fromCategoryDto(@NonNull CategoryDto dto) {
        return Category.builder()
                .id(dto.id())
                .name(dto.name())
                .build();
    }

    @Override
    public CategoryDto fromCategory(@NonNull Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }
}