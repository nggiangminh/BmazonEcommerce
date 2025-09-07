package com.project.backend.dto.response;

import com.project.backend.entity.ProductRecommendation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProductRecommendationDTO {
    
    private UUID id;
    private UUID productId;
    private String productName;
    private String productDescription;
    private String productImage;
    private BigDecimal productPrice;
    private String recommendationType;
    private Double score;
    private String reason;
    private Boolean isViewed;
    private Boolean isClicked;
    private LocalDateTime createdAt;
    
    public ProductRecommendationDTO() {}
    
    public ProductRecommendationDTO(ProductRecommendation recommendation) {
        this.id = recommendation.getId();
        this.productId = recommendation.getProduct().getId();
        this.productName = recommendation.getProduct().getName();
        this.productDescription = recommendation.getProduct().getDescription();
        this.productImage = recommendation.getProduct().getCover();
        this.recommendationType = recommendation.getRecommendationType();
        this.score = recommendation.getScore();
        this.reason = recommendation.getReason();
        this.isViewed = recommendation.getIsViewed();
        this.isClicked = recommendation.getIsClicked();
        this.createdAt = recommendation.getCreatedAt();
        
        // Get product price (minimum price from SKUs)
        if (recommendation.getProduct().getProductSkus() != null && 
            !recommendation.getProduct().getProductSkus().isEmpty()) {
            this.productPrice = recommendation.getProduct().getProductSkus().stream()
                .filter(sku -> sku.getDeletedAt() == null && sku.getPrice() != null)
                .map(sku -> sku.getPrice())
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        }
    }
    
    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }
    
    public String getProductImage() { return productImage; }
    public void setProductImage(String productImage) { this.productImage = productImage; }
    
    public BigDecimal getProductPrice() { return productPrice; }
    public void setProductPrice(BigDecimal productPrice) { this.productPrice = productPrice; }
    
    public String getRecommendationType() { return recommendationType; }
    public void setRecommendationType(String recommendationType) { this.recommendationType = recommendationType; }
    
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    
    public Boolean getIsViewed() { return isViewed; }
    public void setIsViewed(Boolean isViewed) { this.isViewed = isViewed; }
    
    public Boolean getIsClicked() { return isClicked; }
    public void setIsClicked(Boolean isClicked) { this.isClicked = isClicked; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
