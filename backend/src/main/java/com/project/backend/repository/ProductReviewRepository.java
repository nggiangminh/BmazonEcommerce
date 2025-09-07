package com.project.backend.repository;

import com.project.backend.entity.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, UUID> {
    
    // Find reviews by product ID that are not deleted and approved
    @Query("SELECT pr FROM ProductReview pr WHERE pr.product.id = :productId AND pr.deletedAt IS NULL AND pr.isApproved = true")
    Page<ProductReview> findByProductIdActive(@Param("productId") UUID productId, Pageable pageable);
    
    // Find reviews by product ID that are not deleted and approved
    @Query("SELECT pr FROM ProductReview pr WHERE pr.product.id = :productId AND pr.deletedAt IS NULL AND pr.isApproved = true")
    List<ProductReview> findByProductIdActive(@Param("productId") UUID productId);
    
    // Find review by user and product
    @Query("SELECT pr FROM ProductReview pr WHERE pr.user.id = :userId AND pr.product.id = :productId AND pr.deletedAt IS NULL")
    Optional<ProductReview> findByUserIdAndProductId(@Param("userId") UUID userId, @Param("productId") UUID productId);
    
    // Find reviews by user ID that are not deleted
    @Query("SELECT pr FROM ProductReview pr WHERE pr.user.id = :userId AND pr.deletedAt IS NULL")
    Page<ProductReview> findByUserIdActive(@Param("userId") UUID userId, Pageable pageable);
    
    // Find reviews by user ID that are not deleted
    @Query("SELECT pr FROM ProductReview pr WHERE pr.user.id = :userId AND pr.deletedAt IS NULL")
    List<ProductReview> findByUserIdActive(@Param("userId") UUID userId);
    
    // Find reviews by rating that are not deleted and approved
    @Query("SELECT pr FROM ProductReview pr WHERE pr.rating = :rating AND pr.deletedAt IS NULL AND pr.isApproved = true")
    Page<ProductReview> findByRatingActive(@Param("rating") Integer rating, Pageable pageable);
    
    // Count reviews by product ID that are not deleted and approved
    @Query("SELECT COUNT(pr) FROM ProductReview pr WHERE pr.product.id = :productId AND pr.deletedAt IS NULL AND pr.isApproved = true")
    long countByProductIdActive(@Param("productId") UUID productId);
    
    // Count reviews by rating for a product that are not deleted and approved
    @Query("SELECT COUNT(pr) FROM ProductReview pr WHERE pr.product.id = :productId AND pr.rating = :rating AND pr.deletedAt IS NULL AND pr.isApproved = true")
    long countByProductIdAndRatingActive(@Param("productId") UUID productId, @Param("rating") Integer rating);
    
    // Calculate average rating for a product
    @Query("SELECT AVG(pr.rating) FROM ProductReview pr WHERE pr.product.id = :productId AND pr.deletedAt IS NULL AND pr.isApproved = true")
    Double getAverageRatingByProductId(@Param("productId") UUID productId);
    
    // Find most helpful reviews (highest helpful count)
    @Query("SELECT pr FROM ProductReview pr WHERE pr.product.id = :productId AND pr.deletedAt IS NULL AND pr.isApproved = true ORDER BY pr.helpfulCount DESC")
    Page<ProductReview> findMostHelpfulByProductId(@Param("productId") UUID productId, Pageable pageable);
    
    // Find recent reviews
    @Query("SELECT pr FROM ProductReview pr WHERE pr.product.id = :productId AND pr.deletedAt IS NULL AND pr.isApproved = true ORDER BY pr.createdAt DESC")
    Page<ProductReview> findRecentByProductId(@Param("productId") UUID productId, Pageable pageable);
    
    // Find verified purchase reviews
    @Query("SELECT pr FROM ProductReview pr WHERE pr.product.id = :productId AND pr.deletedAt IS NULL AND pr.isApproved = true AND pr.isVerifiedPurchase = true")
    Page<ProductReview> findVerifiedPurchaseByProductId(@Param("productId") UUID productId, Pageable pageable);
    
    // Check if user has reviewed a product
    @Query("SELECT COUNT(pr) > 0 FROM ProductReview pr WHERE pr.user.id = :userId AND pr.product.id = :productId AND pr.deletedAt IS NULL")
    boolean existsByUserIdAndProductId(@Param("userId") UUID userId, @Param("productId") UUID productId);
    
    // Find pending reviews (not approved)
    @Query("SELECT pr FROM ProductReview pr WHERE pr.isApproved = false AND pr.deletedAt IS NULL")
    Page<ProductReview> findPendingReviews(Pageable pageable);
    
    // Find reviews by rating range
    @Query("SELECT pr FROM ProductReview pr WHERE pr.product.id = :productId AND pr.rating BETWEEN :minRating AND :maxRating AND pr.deletedAt IS NULL AND pr.isApproved = true")
    Page<ProductReview> findByProductIdAndRatingRange(@Param("productId") UUID productId, 
                                                     @Param("minRating") Integer minRating, 
                                                     @Param("maxRating") Integer maxRating, 
                                                     Pageable pageable);
}
