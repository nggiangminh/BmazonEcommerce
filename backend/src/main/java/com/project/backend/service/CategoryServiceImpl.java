package com.project.backend.service;

import com.project.backend.entity.Category;
import com.project.backend.exception.DuplicateResourceException;
import com.project.backend.exception.ResourceNotFoundException;
import com.project.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAllActive();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryById(UUID id) {
        return categoryRepository.findByIdActive(id);
    }
    
    @Override
    public Category createCategory(Category category) {
        // Check if category name already exists
        if (categoryRepository.existsByNameActive(category.getName())) {
            throw new DuplicateResourceException("Category with name '" + category.getName() + "' already exists");
        }
        
        if (category.getCreatedAt() == null) {
            category.setCreatedAt(LocalDateTime.now());
        }
        
        return categoryRepository.save(category);
    }
    
    @Override
    public Category updateCategory(UUID id, Category category) {
        Category existingCategory = categoryRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        // Check if new name conflicts with existing categories
        if (!existingCategory.getName().equals(category.getName()) && 
            categoryRepository.existsByNameAndIdNotActive(category.getName(), id)) {
            throw new DuplicateResourceException("Category with name '" + category.getName() + "' already exists");
        }
        
        // Update fields
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        
        return categoryRepository.save(existingCategory);
    }
    
    @Override
    public void deleteCategory(UUID id) {
        Category category = categoryRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }
    
    @Override
    public void softDeleteCategory(UUID id) {
        Category category = categoryRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        category.softDelete();
        categoryRepository.save(category);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Category> searchCategoriesByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCaseActive(name);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByNameActive(name);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getTotalCategoryCount() {
        return categoryRepository.countActive();
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return categoryRepository.findByIdActive(id).isPresent();
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return categoryRepository.existsByNameActive(name);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByNameAndIdNot(String name, UUID id) {
        return categoryRepository.existsByNameAndIdNotActive(name, id);
    }
}
