package com.project.backend.dto.response;

import com.project.backend.entity.Wishlist;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class WishlistDTO {
    
    private UUID id;
    private UUID userId;
    private ProductDTO product;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public WishlistDTO() {}
    
    public WishlistDTO(Wishlist wishlist) {
        this.id = wishlist.getId();
        this.userId = wishlist.getUser().getId();
        this.product = new ProductDTO(wishlist.getProduct());
        this.createdAt = wishlist.getCreatedAt();
        this.updatedAt = wishlist.getUpdatedAt();
    }
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public UUID getUserId() {
        return userId;
    }
    
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    
    public ProductDTO getProduct() {
        return product;
    }
    
    public void setProduct(ProductDTO product) {
        this.product = product;
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
    
    // Nested ProductDTO (simplified version for wishlist)
    public static class ProductDTO {
        private UUID id;
        private String name;
        private String description;
        private String summary;
        private String cover;
        private String categoryName;
        private boolean available;
        private Double minPrice;
        private Double maxPrice;
        
        public ProductDTO() {}
        
        public ProductDTO(com.project.backend.entity.Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.description = product.getDescription();
            this.summary = product.getSummary();
            this.cover = product.getCover();
            this.categoryName = product.getCategory() != null ? product.getCategory().getName() : null;
            this.available = product.getProductSkus() != null && 
                product.getProductSkus().stream()
                    .anyMatch(sku -> sku.getDeletedAt() == null && sku.getQuantity() > 0);
            
            // Calculate min and max price
            if (product.getProductSkus() != null && !product.getProductSkus().isEmpty()) {
                this.minPrice = product.getProductSkus().stream()
                    .filter(sku -> sku.getDeletedAt() == null && sku.getPrice() != null)
                    .mapToDouble(sku -> sku.getPrice().doubleValue())
                    .min()
                    .orElse(0.0);
                
                this.maxPrice = product.getProductSkus().stream()
                    .filter(sku -> sku.getDeletedAt() == null && sku.getPrice() != null)
                    .mapToDouble(sku -> sku.getPrice().doubleValue())
                    .max()
                    .orElse(0.0);
            }
        }
        
        // Getters and setters
        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getSummary() { return summary; }
        public void setSummary(String summary) { this.summary = summary; }
        
        public String getCover() { return cover; }
        public void setCover(String cover) { this.cover = cover; }
        
        public String getCategoryName() { return categoryName; }
        public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
        
        public boolean isAvailable() { return available; }
        public void setAvailable(boolean available) { this.available = available; }
        
        public Double getMinPrice() { return minPrice; }
        public void setMinPrice(Double minPrice) { this.minPrice = minPrice; }
        
        public Double getMaxPrice() { return maxPrice; }
        public void setMaxPrice(Double maxPrice) { this.maxPrice = maxPrice; }
    }
}
