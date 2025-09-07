package com.project.backend.service;

import com.project.backend.dto.request.CreateProductReviewRequest;
import com.project.backend.dto.request.UpdateProductReviewRequest;
import com.project.backend.dto.response.ProductDetailDTO;
import com.project.backend.dto.response.ProductReviewDTO;
import com.project.backend.dto.response.ProductRecommendationDTO;
import com.project.backend.entity.Product;
import com.project.backend.entity.ProductReview;
import com.project.backend.entity.ProductRecommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductManagementService {
    
    // Product detail management
    ProductDetailDTO getProductDetails(UUID productId, UUID userId);
    ProductDetailDTO getProductDetails(UUID productId); // For anonymous users
    void trackProductView(UUID productId, UUID userId, String ipAddress, String userAgent);
    void trackProductView(UUID productId, String ipAddress, String userAgent); // For anonymous users
    
    // Product review management
    ProductReviewDTO createProductReview(UUID userId, UUID productId, CreateProductReviewRequest request);
    ProductReviewDTO updateProductReview(UUID userId, UUID reviewId, UpdateProductReviewRequest request);
    void deleteProductReview(UUID userId, UUID reviewId);
    Page<ProductReviewDTO> getProductReviews(UUID productId, Pageable pageable);
    List<ProductReviewDTO> getProductReviews(UUID productId);
    ProductReviewDTO getProductReview(UUID reviewId);
    boolean hasUserReviewedProduct(UUID userId, UUID productId);
    ProductReviewDTO getUserReviewForProduct(UUID userId, UUID productId);
    
    // Review statistics
    ProductReviewStatistics getProductReviewStatistics(UUID productId);
    List<ProductReviewDTO> getRecentProductReviews(UUID productId, int limit);
    List<ProductReviewDTO> getMostHelpfulReviews(UUID productId, int limit);
    List<ProductReviewDTO> getVerifiedPurchaseReviews(UUID productId, int limit);
    
    // Product recommendations
    List<ProductRecommendationDTO> getProductRecommendations(UUID userId, int limit);
    List<ProductRecommendationDTO> getSimilarProducts(UUID productId, int limit);
    List<ProductRecommendationDTO> getTrendingProducts(int limit);
    List<ProductRecommendationDTO> getPersonalizedRecommendations(UUID userId, int limit);
    void markRecommendationAsViewed(UUID userId, UUID recommendationId);
    void markRecommendationAsClicked(UUID userId, UUID recommendationId);
    
    // Recommendation management
    void generateRecommendationsForUser(UUID userId);
    void updateRecommendationScore(UUID recommendationId, Double newScore);
    void removeRecommendation(UUID recommendationId);
    
    // Analytics and insights
    ProductAnalytics getProductAnalytics(UUID productId);
    List<ProductViewStatistics> getProductViewStatistics(UUID productId);
    List<ProductRecommendationStatistics> getRecommendationStatistics();
    
    // Inner classes for DTOs and statistics
    class ProductReviewStatistics {
        private long totalReviews;
        private double averageRating;
        private long rating1Count;
        private long rating2Count;
        private long rating3Count;
        private long rating4Count;
        private long rating5Count;
        private long verifiedPurchaseCount;
        
        public ProductReviewStatistics() {}
        
        public ProductReviewStatistics(long totalReviews, double averageRating, long rating1Count, 
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
    
    class ProductAnalytics {
        private UUID productId;
        private long totalViews;
        private long uniqueViewers;
        private long totalRecommendations;
        private long clickedRecommendations;
        private double clickThroughRate;
        private long totalReviews;
        private double averageRating;
        
        public ProductAnalytics() {}
        
        public ProductAnalytics(UUID productId, long totalViews, long uniqueViewers, 
                              long totalRecommendations, long clickedRecommendations, 
                              double clickThroughRate, long totalReviews, double averageRating) {
            this.productId = productId;
            this.totalViews = totalViews;
            this.uniqueViewers = uniqueViewers;
            this.totalRecommendations = totalRecommendations;
            this.clickedRecommendations = clickedRecommendations;
            this.clickThroughRate = clickThroughRate;
            this.totalReviews = totalReviews;
            this.averageRating = averageRating;
        }
        
        // Getters and setters
        public UUID getProductId() { return productId; }
        public void setProductId(UUID productId) { this.productId = productId; }
        
        public long getTotalViews() { return totalViews; }
        public void setTotalViews(long totalViews) { this.totalViews = totalViews; }
        
        public long getUniqueViewers() { return uniqueViewers; }
        public void setUniqueViewers(long uniqueViewers) { this.uniqueViewers = uniqueViewers; }
        
        public long getTotalRecommendations() { return totalRecommendations; }
        public void setTotalRecommendations(long totalRecommendations) { this.totalRecommendations = totalRecommendations; }
        
        public long getClickedRecommendations() { return clickedRecommendations; }
        public void setClickedRecommendations(long clickedRecommendations) { this.clickedRecommendations = clickedRecommendations; }
        
        public double getClickThroughRate() { return clickThroughRate; }
        public void setClickThroughRate(double clickThroughRate) { this.clickThroughRate = clickThroughRate; }
        
        public long getTotalReviews() { return totalReviews; }
        public void setTotalReviews(long totalReviews) { this.totalReviews = totalReviews; }
        
        public double getAverageRating() { return averageRating; }
        public void setAverageRating(double averageRating) { this.averageRating = averageRating; }
    }
    
    class ProductViewStatistics {
        private String date;
        private long viewCount;
        private long uniqueViewers;
        
        public ProductViewStatistics() {}
        
        public ProductViewStatistics(String date, long viewCount, long uniqueViewers) {
            this.date = date;
            this.viewCount = viewCount;
            this.uniqueViewers = uniqueViewers;
        }
        
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        
        public long getViewCount() { return viewCount; }
        public void setViewCount(long viewCount) { this.viewCount = viewCount; }
        
        public long getUniqueViewers() { return uniqueViewers; }
        public void setUniqueViewers(long uniqueViewers) { this.uniqueViewers = uniqueViewers; }
    }
    
    class ProductRecommendationStatistics {
        private String recommendationType;
        private long totalRecommendations;
        private long viewedRecommendations;
        private long clickedRecommendations;
        private double clickThroughRate;
        
        public ProductRecommendationStatistics() {}
        
        public ProductRecommendationStatistics(String recommendationType, long totalRecommendations, 
                                             long viewedRecommendations, long clickedRecommendations, 
                                             double clickThroughRate) {
            this.recommendationType = recommendationType;
            this.totalRecommendations = totalRecommendations;
            this.viewedRecommendations = viewedRecommendations;
            this.clickedRecommendations = clickedRecommendations;
            this.clickThroughRate = clickThroughRate;
        }
        
        public String getRecommendationType() { return recommendationType; }
        public void setRecommendationType(String recommendationType) { this.recommendationType = recommendationType; }
        
        public long getTotalRecommendations() { return totalRecommendations; }
        public void setTotalRecommendations(long totalRecommendations) { this.totalRecommendations = totalRecommendations; }
        
        public long getViewedRecommendations() { return viewedRecommendations; }
        public void setViewedRecommendations(long viewedRecommendations) { this.viewedRecommendations = viewedRecommendations; }
        
        public long getClickedRecommendations() { return clickedRecommendations; }
        public void setClickedRecommendations(long clickedRecommendations) { this.clickedRecommendations = clickedRecommendations; }
        
        public double getClickThroughRate() { return clickThroughRate; }
        public void setClickThroughRate(double clickThroughRate) { this.clickThroughRate = clickThroughRate; }
    }
}
