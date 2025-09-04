package com.fahmi.personalonlinestore.service.impl;

import com.fahmi.personalonlinestore.entity.Category;
import com.fahmi.personalonlinestore.repository.CategoryRepository;
import com.fahmi.personalonlinestore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category updateCategory(String id, Category category) {
        return categoryRepository.findById(id).map(c -> {
            c.setName(category.getName());
            return categoryRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}
