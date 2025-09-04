package com.fahmi.personalonlinestore.mapper;

import com.fahmi.personalonlinestore.dto.request.CategoryRequest;
import com.fahmi.personalonlinestore.dto.response.CategoryResponse;
import com.fahmi.personalonlinestore.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public static Category fromRequest(CategoryRequest request) {
        return Category.builder()
                .name(request.getName())
                .build();
    }

    public static CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}

