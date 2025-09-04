package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);

    List<Category> getAllCategories();

    Category getCategoryById(String id);

    Category updateCategory(String id, Category category);

    void deleteCategory(String id);
}

