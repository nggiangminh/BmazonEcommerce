package com.project.backend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product_recommendations")
public class ProductRecommendation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(name = "recommendation_type", nullable = false)
    private String recommendationType; // "similar", "trending", "personalized", "category_based"
    
    @Column(name = "score", nullable = false)
    private Double score; // Recommendation score (0.0 - 1.0)
    
    @Column(name = "reason")
    private String reason; // Reason for recommendation
    
    @Column(name = "is_viewed", nullable = false)
    private Boolean isViewed = false;
    
    @Column(name = "is_clicked", nullable = false)
    private Boolean isClicked = false;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    public ProductRecommendation() {}
    
    // Getters and setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public String getRecommendationType() {
        return recommendationType;
    }
    
    public void setRecommendationType(String recommendationType) {
        this.recommendationType = recommendationType;
    }
    
    public Double getScore() {
        return score;
    }
    
    public void setScore(Double score) {
        this.score = score;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public Boolean getIsViewed() {
        return isViewed;
    }
    
    public void setIsViewed(Boolean isViewed) {
        this.isViewed = isViewed;
    }
    
    public Boolean getIsClicked() {
        return isClicked;
    }
    
    public void setIsClicked(Boolean isClicked) {
        this.isClicked = isClicked;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    // Helper methods
    public void markAsViewed() {
        this.isViewed = true;
    }
    
    public void markAsClicked() {
        this.isClicked = true;
    }
}
