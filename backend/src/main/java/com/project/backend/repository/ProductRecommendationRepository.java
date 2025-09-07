package com.project.backend.repository;

import com.project.backend.entity.ProductRecommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRecommendationRepository extends JpaRepository<ProductRecommendation, UUID> {
    
    // Find recommendations by user ID
    @Query("SELECT pr FROM ProductRecommendation pr WHERE pr.user.id = :userId ORDER BY pr.score DESC")
    Page<ProductRecommendation> findByUserId(@Param("userId") UUID userId, Pageable pageable);
    
    // Find recommendations by user ID
    @Query("SELECT pr FROM ProductRecommendation pr WHERE pr.user.id = :userId ORDER BY pr.score DESC")
    List<ProductRecommendation> findByUserId(@Param("userId") UUID userId);
    
    // Find recommendations by user ID and type
    @Query("SELECT pr FROM ProductRecommendation pr WHERE pr.user.id = :userId AND pr.recommendationType = :type ORDER BY pr.score DESC")
    List<ProductRecommendation> findByUserIdAndType(@Param("userId") UUID userId, @Param("type") String type);
    
    // Find unviewed recommendations by user ID
    @Query("SELECT pr FROM ProductRecommendation pr WHERE pr.user.id = :userId AND pr.isViewed = false ORDER BY pr.score DESC")
    List<ProductRecommendation> findUnviewedByUserId(@Param("userId") UUID userId);
    
    // Find clicked recommendations by user ID
    @Query("SELECT pr FROM ProductRecommendation pr WHERE pr.user.id = :userId AND pr.isClicked = true ORDER BY pr.createdAt DESC")
    List<ProductRecommendation> findClickedByUserId(@Param("userId") UUID userId);
    
    // Find recommendations by product ID
    @Query("SELECT pr FROM ProductRecommendation pr WHERE pr.product.id = :productId")
    List<ProductRecommendation> findByProductId(@Param("productId") UUID productId);
    
    // Find recommendations by type
    @Query("SELECT pr FROM ProductRecommendation pr WHERE pr.recommendationType = :type ORDER BY pr.score DESC")
    List<ProductRecommendation> findByType(@Param("type") String type);
    
    // Count recommendations by user ID
    @Query("SELECT COUNT(pr) FROM ProductRecommendation pr WHERE pr.user.id = :userId")
    long countByUserId(@Param("userId") UUID userId);
    
    // Count unviewed recommendations by user ID
    @Query("SELECT COUNT(pr) FROM ProductRecommendation pr WHERE pr.user.id = :userId AND pr.isViewed = false")
    long countUnviewedByUserId(@Param("userId") UUID userId);
    
    // Count clicked recommendations by user ID
    @Query("SELECT COUNT(pr) FROM ProductRecommendation pr WHERE pr.user.id = :userId AND pr.isClicked = true")
    long countClickedByUserId(@Param("userId") UUID userId);
    
    // Find top recommendations by score
    @Query("SELECT pr FROM ProductRecommendation pr ORDER BY pr.score DESC")
    List<ProductRecommendation> findTopByScore(@Param("limit") int limit);
    
    // Find recommendations by user ID with minimum score
    @Query("SELECT pr FROM ProductRecommendation pr WHERE pr.user.id = :userId AND pr.score >= :minScore ORDER BY pr.score DESC")
    List<ProductRecommendation> findByUserIdAndMinScore(@Param("userId") UUID userId, @Param("minScore") Double minScore);
    
    // Check if recommendation exists for user and product
    @Query("SELECT COUNT(pr) > 0 FROM ProductRecommendation pr WHERE pr.user.id = :userId AND pr.product.id = :productId")
    boolean existsByUserIdAndProductId(@Param("userId") UUID userId, @Param("productId") UUID productId);
    
    // Find most popular recommendation types
    @Query("SELECT pr.recommendationType, COUNT(pr) as count FROM ProductRecommendation pr GROUP BY pr.recommendationType ORDER BY count DESC")
    List<Object[]> findMostPopularRecommendationTypes();
    
    // Find recommendations with highest click-through rate
    @Query("SELECT pr.product.id, COUNT(pr) as total, SUM(CASE WHEN pr.isClicked = true THEN 1 ELSE 0 END) as clicks FROM ProductRecommendation pr GROUP BY pr.product.id HAVING COUNT(pr) > 0 ORDER BY (SUM(CASE WHEN pr.isClicked = true THEN 1 ELSE 0 END) * 1.0 / COUNT(pr)) DESC")
    List<Object[]> findRecommendationsWithHighestCTR(@Param("limit") int limit);
}
