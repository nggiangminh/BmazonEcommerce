package com.project.backend.repository;

import com.project.backend.entity.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductViewRepository extends JpaRepository<ProductView, UUID> {
    
    // Find views by product ID
    @Query("SELECT pv FROM ProductView pv WHERE pv.product.id = :productId")
    List<ProductView> findByProductId(@Param("productId") UUID productId);
    
    // Find views by user ID
    @Query("SELECT pv FROM ProductView pv WHERE pv.user.id = :userId")
    List<ProductView> findByUserId(@Param("userId") UUID userId);
    
    // Find views by product ID within date range
    @Query("SELECT pv FROM ProductView pv WHERE pv.product.id = :productId AND pv.viewedAt BETWEEN :startDate AND :endDate")
    List<ProductView> findByProductIdAndDateRange(@Param("productId") UUID productId, 
                                                 @Param("startDate") LocalDateTime startDate, 
                                                 @Param("endDate") LocalDateTime endDate);
    
    // Count views by product ID
    @Query("SELECT COUNT(pv) FROM ProductView pv WHERE pv.product.id = :productId")
    long countByProductId(@Param("productId") UUID productId);
    
    // Count views by product ID within date range
    @Query("SELECT COUNT(pv) FROM ProductView pv WHERE pv.product.id = :productId AND pv.viewedAt BETWEEN :startDate AND :endDate")
    long countByProductIdAndDateRange(@Param("productId") UUID productId, 
                                     @Param("startDate") LocalDateTime startDate, 
                                     @Param("endDate") LocalDateTime endDate);
    
    // Count views by user ID
    @Query("SELECT COUNT(pv) FROM ProductView pv WHERE pv.user.id = :userId")
    long countByUserId(@Param("userId") UUID userId);
    
    // Find most viewed products (by view count)
    @Query("SELECT pv.product.id, COUNT(pv) as viewCount FROM ProductView pv GROUP BY pv.product.id ORDER BY viewCount DESC")
    List<Object[]> findMostViewedProducts(@Param("limit") int limit);
    
    // Find recent views by user ID
    @Query("SELECT pv FROM ProductView pv WHERE pv.user.id = :userId ORDER BY pv.viewedAt DESC")
    List<ProductView> findRecentViewsByUserId(@Param("userId") UUID userId, @Param("limit") int limit);
    
    // Find unique product views by user ID (distinct products)
    @Query("SELECT DISTINCT pv.product.id FROM ProductView pv WHERE pv.user.id = :userId")
    List<UUID> findDistinctProductIdsByUserId(@Param("userId") UUID userId);
    
    // Check if user has viewed a product
    @Query("SELECT COUNT(pv) > 0 FROM ProductView pv WHERE pv.user.id = :userId AND pv.product.id = :productId")
    boolean existsByUserIdAndProductId(@Param("userId") UUID userId, @Param("productId") UUID productId);
    
    // Find views by IP address
    @Query("SELECT pv FROM ProductView pv WHERE pv.ipAddress = :ipAddress")
    List<ProductView> findByIpAddress(@Param("ipAddress") String ipAddress);
    
    // Count unique viewers for a product
    @Query("SELECT COUNT(DISTINCT pv.user.id) FROM ProductView pv WHERE pv.product.id = :productId AND pv.user.id IS NOT NULL")
    long countUniqueViewersByProductId(@Param("productId") UUID productId);
}
