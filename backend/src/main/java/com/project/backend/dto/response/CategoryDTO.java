package com.project.backend.dto.response;

import com.project.backend.entity.Category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CategoryDTO {
    
    private UUID id;
    private String name;
    private String description;
    private List<CategoryDTO> subCategories;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long productCount;
    
    public CategoryDTO() {}
    
    // Constructor for SubCategory
    public CategoryDTO(UUID id, String name, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.subCategories = null; // SubCategories don't have subcategories
        this.productCount = 0; // SubCategories don't have direct product count
    }
    
    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.subCategories = category.getSubCategories() != null ? 
            category.getSubCategories().stream()
                .filter(sub -> sub.getDeleted_at() == null)
                .map(sub -> new CategoryDTO(sub.getId(), sub.getName(), sub.getDescription(), sub.getCreated_at()))
                .collect(Collectors.toList()) : null;
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
        this.productCount = category.getProducts() != null ? 
            category.getProducts().stream()
                .filter(product -> product.getDeletedAt() == null)
                .count() : 0;
    }
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<CategoryDTO> getSubCategories() {
        return subCategories;
    }
    
    public void setSubCategories(List<CategoryDTO> subCategories) {
        this.subCategories = subCategories;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public long getProductCount() {
        return productCount;
    }
    
    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }
}
