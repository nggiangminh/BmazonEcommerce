package com.project.backend.repository;

import com.project.backend.entity.Wishlist;
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
public interface WishlistRepository extends JpaRepository<Wishlist, UUID> {
    
    // Find wishlist items by user ID that are not deleted
    @Query("SELECT w FROM Wishlist w WHERE w.user.id = :userId AND w.deletedAt IS NULL")
    List<Wishlist> findByUserIdActive(@Param("userId") UUID userId);
    
    // Find wishlist items by user ID with pagination that are not deleted
    @Query("SELECT w FROM Wishlist w WHERE w.user.id = :userId AND w.deletedAt IS NULL")
    Page<Wishlist> findByUserIdActive(@Param("userId") UUID userId, Pageable pageable);
    
    // Find wishlist items by user ID with product details that are not deleted
    @Query("SELECT w FROM Wishlist w LEFT JOIN FETCH w.product p LEFT JOIN FETCH p.category WHERE w.user.id = :userId AND w.deletedAt IS NULL")
    List<Wishlist> findByUserIdWithProductDetails(@Param("userId") UUID userId);
    
    // Find wishlist item by user ID and product ID that is not deleted
    @Query("SELECT w FROM Wishlist w WHERE w.user.id = :userId AND w.product.id = :productId AND w.deletedAt IS NULL")
    Optional<Wishlist> findByUserIdAndProductIdActive(@Param("userId") UUID userId, @Param("productId") UUID productId);
    
    // Find wishlist item by user ID and product ID (including deleted)
    @Query("SELECT w FROM Wishlist w WHERE w.user.id = :userId AND w.product.id = :productId")
    Optional<Wishlist> findByUserIdAndProductId(@Param("userId") UUID userId, @Param("productId") UUID productId);
    
    // Check if product is in user's wishlist (not deleted)
    @Query("SELECT COUNT(w) > 0 FROM Wishlist w WHERE w.user.id = :userId AND w.product.id = :productId AND w.deletedAt IS NULL")
    boolean existsByUserIdAndProductIdActive(@Param("userId") UUID userId, @Param("productId") UUID productId);
    
    // Count wishlist items by user ID that are not deleted
    @Query("SELECT COUNT(w) FROM Wishlist w WHERE w.user.id = :userId AND w.deletedAt IS NULL")
    long countByUserIdActive(@Param("userId") UUID userId);
    
    // Find wishlist items by product ID that are not deleted
    @Query("SELECT w FROM Wishlist w WHERE w.product.id = :productId AND w.deletedAt IS NULL")
    List<Wishlist> findByProductIdActive(@Param("productId") UUID productId);
    
    // Count wishlist items by product ID that are not deleted
    @Query("SELECT COUNT(w) FROM Wishlist w WHERE w.product.id = :productId AND w.deletedAt IS NULL")
    long countByProductIdActive(@Param("productId") UUID productId);
    
    // Find most wishlisted products
    @Query("SELECT w.product, COUNT(w) as wishlistCount FROM Wishlist w WHERE w.deletedAt IS NULL GROUP BY w.product ORDER BY wishlistCount DESC")
    List<Object[]> findMostWishlistedProducts(Pageable pageable);
    
    // Find recent wishlist items by user ID that are not deleted
    @Query("SELECT w FROM Wishlist w WHERE w.user.id = :userId AND w.deletedAt IS NULL ORDER BY w.createdAt DESC")
    List<Wishlist> findRecentByUserId(@Param("userId") UUID userId, Pageable pageable);
    
    // Find wishlist items by category for user that are not deleted
    @Query("SELECT w FROM Wishlist w WHERE w.user.id = :userId AND w.product.category.id = :categoryId AND w.deletedAt IS NULL")
    List<Wishlist> findByUserIdAndCategoryIdActive(@Param("userId") UUID userId, @Param("categoryId") UUID categoryId);
    
    // Delete wishlist items by user ID (soft delete)
    @Query("UPDATE Wishlist w SET w.deletedAt = CURRENT_TIMESTAMP WHERE w.user.id = :userId")
    void softDeleteByUserId(@Param("userId") UUID userId);
    
    // Delete wishlist item by user ID and product ID (soft delete)
    @Query("UPDATE Wishlist w SET w.deletedAt = CURRENT_TIMESTAMP WHERE w.user.id = :userId AND w.product.id = :productId")
    void softDeleteByUserIdAndProductId(@Param("userId") UUID userId, @Param("productId") UUID productId);
    
    // Restore wishlist item by user ID and product ID
    @Query("UPDATE Wishlist w SET w.deletedAt = NULL WHERE w.user.id = :userId AND w.product.id = :productId")
    void restoreByUserIdAndProductId(@Param("userId") UUID userId, @Param("productId") UUID productId);
    
    // Count total wishlist items (not deleted)
    @Query("SELECT COUNT(w) FROM Wishlist w WHERE w.deletedAt IS NULL")
    long countActive();
    
    // Count total wishlist items (including deleted)
    @Query("SELECT COUNT(w) FROM Wishlist w")
    long countAll();
}
