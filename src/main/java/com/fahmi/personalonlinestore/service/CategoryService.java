package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.dto.request.CategoryRequest;
import com.fahmi.personalonlinestore.dto.response.CategoryResponse;
import com.fahmi.personalonlinestore.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    List<CategoryResponse> getAllCategories();

    Category getCategoryById(String id);

    CategoryResponse updateCategory(String id, CategoryRequest request);

    void deleteCategory(String id);
}

