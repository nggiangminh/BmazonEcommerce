package com.project.backend.service;

import com.project.backend.entity.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    
    // Basic CRUD operations
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(UUID id);
    Category createCategory(Category category);
    Category updateCategory(UUID id, Category category);
    void deleteCategory(UUID id);
    void softDeleteCategory(UUID id);
    
    // Search operations
    List<Category> searchCategoriesByName(String name);
    Optional<Category> getCategoryByName(String name);
    
    // Statistics
    long getTotalCategoryCount();
    
    // Validation
    boolean existsById(UUID id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
