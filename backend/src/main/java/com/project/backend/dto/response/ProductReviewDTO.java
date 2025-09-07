package com.project.backend.dto.response;

import com.project.backend.entity.ProductReview;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductReviewDTO {
    
    private UUID id;
    private UUID productId;
    private String productName;
    private UUID userId;
    private String userName;
    private String userAvatar;
    private Integer rating;
    private String title;
    private String comment;
    private Boolean isVerifiedPurchase;
    private Integer helpfulCount;
    private Boolean isApproved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public ProductReviewDTO() {}
    
    public ProductReviewDTO(ProductReview review) {
        this.id = review.getId();
        this.productId = review.getProduct().getId();
        this.productName = review.getProduct().getName();
        this.userId = review.getUser().getId();
        this.userName = review.getUser().getFirstName() + " " + review.getUser().getLastName();
        this.userAvatar = review.getUser().getAvatar();
        this.rating = review.getRating();
        this.title = review.getTitle();
        this.comment = review.getComment();
        this.isVerifiedPurchase = review.getIsVerifiedPurchase();
        this.helpfulCount = review.getHelpfulCount();
        this.isApproved = review.getIsApproved();
        this.createdAt = review.getCreatedAt();
        this.updatedAt = review.getUpdatedAt();
    }
    
    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getUserAvatar() { return userAvatar; }
    public void setUserAvatar(String userAvatar) { this.userAvatar = userAvatar; }
    
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    
    public Boolean getIsVerifiedPurchase() { return isVerifiedPurchase; }
    public void setIsVerifiedPurchase(Boolean isVerifiedPurchase) { this.isVerifiedPurchase = isVerifiedPurchase; }
    
    public Integer getHelpfulCount() { return helpfulCount; }
    public void setHelpfulCount(Integer helpfulCount) { this.helpfulCount = helpfulCount; }
    
    public Boolean getIsApproved() { return isApproved; }
    public void setIsApproved(Boolean isApproved) { this.isApproved = isApproved; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
