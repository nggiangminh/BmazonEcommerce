package com.project.backend.dto.response;

import com.project.backend.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductDetailDTO {
    
    private UUID id;
    private String name;
    private String description;
    private String summary;
    private String cover;
    private CategoryDTO category;
    private List<ProductSkuDTO> productSkus;
    private ProductReviewStatisticsDTO reviewStatistics;
    private List<ProductReviewDTO> recentReviews;
    private List<ProductDTO> similarProducts;
    private List<ProductDTO> recommendedProducts;
    private boolean available;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private int totalStock;
    private long viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public ProductDetailDTO() {}
    
    public ProductDetailDTO(Product product) {
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
        this.available = product.getProductSkus() != null && 
            product.getProductSkus().stream()
                .anyMatch(sku -> sku.getDeletedAt() == null && sku.getQuantity() > 0);
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        
        // Calculate price range and total stock
        if (this.productSkus != null && !this.productSkus.isEmpty()) {
            this.minPrice = this.productSkus.stream()
                .map(ProductSkuDTO::getPrice)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
            this.maxPrice = this.productSkus.stream()
                .map(ProductSkuDTO::getPrice)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
            this.totalStock = this.productSkus.stream()
                .mapToInt(ProductSkuDTO::getQuantity)
                .sum();
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
    
    public CategoryDTO getCategory() { return category; }
    public void setCategory(CategoryDTO category) { this.category = category; }
    
    public List<ProductSkuDTO> getProductSkus() { return productSkus; }
    public void setProductSkus(List<ProductSkuDTO> productSkus) { this.productSkus = productSkus; }
    
    public ProductReviewStatisticsDTO getReviewStatistics() { return reviewStatistics; }
    public void setReviewStatistics(ProductReviewStatisticsDTO reviewStatistics) { this.reviewStatistics = reviewStatistics; }
    
    public List<ProductReviewDTO> getRecentReviews() { return recentReviews; }
    public void setRecentReviews(List<ProductReviewDTO> recentReviews) { this.recentReviews = recentReviews; }
    
    public List<ProductDTO> getSimilarProducts() { return similarProducts; }
    public void setSimilarProducts(List<ProductDTO> similarProducts) { this.similarProducts = similarProducts; }
    
    public List<ProductDTO> getRecommendedProducts() { return recommendedProducts; }
    public void setRecommendedProducts(List<ProductDTO> recommendedProducts) { this.recommendedProducts = recommendedProducts; }
    
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    
    public BigDecimal getMinPrice() { return minPrice; }
    public void setMinPrice(BigDecimal minPrice) { this.minPrice = minPrice; }
    
    public BigDecimal getMaxPrice() { return maxPrice; }
    public void setMaxPrice(BigDecimal maxPrice) { this.maxPrice = maxPrice; }
    
    public int getTotalStock() { return totalStock; }
    public void setTotalStock(int totalStock) { this.totalStock = totalStock; }
    
    public long getViewCount() { return viewCount; }
    public void setViewCount(long viewCount) { this.viewCount = viewCount; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    // Nested DTOs
    public static class CategoryDTO {
        private UUID id;
        private String name;
        private String description;
        
        public CategoryDTO() {}
        
        public CategoryDTO(com.project.backend.entity.Category category) {
            this.id = category.getId();
            this.name = category.getName();
            this.description = category.getDescription();
        }
        
        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
    
    public static class ProductSkuDTO {
        private UUID id;
        private String sku;
        private String size;
        private String color;
        private BigDecimal price;
        private Integer quantity;
        
        public ProductSkuDTO() {}
        
        public ProductSkuDTO(com.project.backend.entity.ProductSku productSku) {
            this.id = productSku.getId();
            this.sku = productSku.getSku();
            this.size = productSku.getSizeAttribute() != null ? productSku.getSizeAttribute().getValue() : null;
            this.color = productSku.getColorAttribute() != null ? productSku.getColorAttribute().getValue() : null;
            this.price = productSku.getPrice();
            this.quantity = productSku.getQuantity();
        }
        
        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        
        public String getSku() { return sku; }
        public void setSku(String sku) { this.sku = sku; }
        
        public String getSize() { return size; }
        public void setSize(String size) { this.size = size; }
        
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
    
    public static class ProductReviewStatisticsDTO {
        private long totalReviews;
        private double averageRating;
        private long rating1Count;
        private long rating2Count;
        private long rating3Count;
        private long rating4Count;
        private long rating5Count;
        private long verifiedPurchaseCount;
        
        public ProductReviewStatisticsDTO() {}
        
        public ProductReviewStatisticsDTO(long totalReviews, double averageRating, long rating1Count, 
                                        long rating2Count, long rating3Count, long rating4Count, 
                                        long rating5Count, long verifiedPurchaseCount) {
            this.totalReviews = totalReviews;
            this.averageRating = averageRating;
            this.rating1Count = rating1Count;
            this.rating2Count = rating2Count;
            this.rating3Count = rating3Count;
            this.rating4Count = rating4Count;
            this.rating5Count = rating5Count;
            this.verifiedPurchaseCount = verifiedPurchaseCount;
        }
        
        // Getters and setters
        public long getTotalReviews() { return totalReviews; }
        public void setTotalReviews(long totalReviews) { this.totalReviews = totalReviews; }
        
        public double getAverageRating() { return averageRating; }
        public void setAverageRating(double averageRating) { this.averageRating = averageRating; }
        
        public long getRating1Count() { return rating1Count; }
        public void setRating1Count(long rating1Count) { this.rating1Count = rating1Count; }
        
        public long getRating2Count() { return rating2Count; }
        public void setRating2Count(long rating2Count) { this.rating2Count = rating2Count; }
        
        public long getRating3Count() { return rating3Count; }
        public void setRating3Count(long rating3Count) { this.rating3Count = rating3Count; }
        
        public long getRating4Count() { return rating4Count; }
        public void setRating4Count(long rating4Count) { this.rating4Count = rating4Count; }
        
        public long getRating5Count() { return rating5Count; }
        public void setRating5Count(long rating5Count) { this.rating5Count = rating5Count; }
        
        public long getVerifiedPurchaseCount() { return verifiedPurchaseCount; }
        public void setVerifiedPurchaseCount(long verifiedPurchaseCount) { this.verifiedPurchaseCount = verifiedPurchaseCount; }
    }
}
