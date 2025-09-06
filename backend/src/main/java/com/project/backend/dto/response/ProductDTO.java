package com.project.backend.dto.response;

import com.project.backend.entity.Category;
import com.project.backend.entity.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductDTO {
    
    private UUID id;
    private String name;
    private String description;
    private String summary;
    private String cover;
    private CategoryDTO category;
    private List<ProductSkuDTO> productSkus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean available;
    
    public ProductDTO() {}
    
    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.summary = product.getSummary();
        this.cover = product.getCover();
        this.category = product.getCategory() != null ? new CategoryDTO(product.getCategory()) : null;
        this.productSkus = product.getProductSkus() != null ? 
            product.getProductSkus().stream()
                .filter(sku -> sku.getDeletedAt() == null)
                .map(ProductSkuDTO::new)
                .collect(Collectors.toList()) : null;
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        this.available = product.getProductSkus() != null && 
            product.getProductSkus().stream()
                .anyMatch(sku -> sku.getDeletedAt() == null && sku.getQuantity() > 0);
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
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public String getCover() {
        return cover;
    }
    
    public void setCover(String cover) {
        this.cover = cover;
    }
    
    public CategoryDTO getCategory() {
        return category;
    }
    
    public void setCategory(CategoryDTO category) {
        this.category = category;
    }
    
    public List<ProductSkuDTO> getProductSkus() {
        return productSkus;
    }
    
    public void setProductSkus(List<ProductSkuDTO> productSkus) {
        this.productSkus = productSkus;
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
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    // Nested DTOs
    public static class CategoryDTO {
        private UUID id;
        private String name;
        private String description;
        
        public CategoryDTO() {}
        
        public CategoryDTO(Category category) {
            this.id = category.getId();
            this.name = category.getName();
            this.description = category.getDescription();
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
    }
    
    public static class ProductSkuDTO {
        private UUID id;
        private String sku;
        private String size;
        private String color;
        private Double price;
        private Integer quantity;
        
        public ProductSkuDTO() {}
        
        public ProductSkuDTO(com.project.backend.entity.ProductSku productSku) {
            this.id = productSku.getId();
            this.sku = productSku.getSku();
            this.size = productSku.getSizeAttribute() != null ? productSku.getSizeAttribute().getValue() : null;
            this.color = productSku.getColorAttribute() != null ? productSku.getColorAttribute().getValue() : null;
            this.price = productSku.getPrice() != null ? productSku.getPrice().doubleValue() : null;
            this.quantity = productSku.getQuantity();
        }
        
        public UUID getId() {
            return id;
        }
        
        public void setId(UUID id) {
            this.id = id;
        }
        
        public String getSku() {
            return sku;
        }
        
        public void setSku(String sku) {
            this.sku = sku;
        }
        
        public String getSize() {
            return size;
        }
        
        public void setSize(String size) {
            this.size = size;
        }
        
        public String getColor() {
            return color;
        }
        
        public void setColor(String color) {
            this.color = color;
        }
        
        public Double getPrice() {
            return price;
        }
        
        public void setPrice(Double price) {
            this.price = price;
        }
        
        public Integer getQuantity() {
            return quantity;
        }
        
        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}
