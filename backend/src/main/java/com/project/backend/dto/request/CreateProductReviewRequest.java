package com.project.backend.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateProductReviewRequest {
    
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;
    
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;
    
    @Size(max = 2000, message = "Comment must not exceed 2000 characters")
    private String comment;
    
    private Boolean isVerifiedPurchase = false;
    
    public CreateProductReviewRequest() {}
    
    public CreateProductReviewRequest(Integer rating, String title, String comment, Boolean isVerifiedPurchase) {
        this.rating = rating;
        this.title = title;
        this.comment = comment;
        this.isVerifiedPurchase = isVerifiedPurchase;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Boolean getIsVerifiedPurchase() {
        return isVerifiedPurchase;
    }
    
    public void setIsVerifiedPurchase(Boolean isVerifiedPurchase) {
        this.isVerifiedPurchase = isVerifiedPurchase;
    }
}
