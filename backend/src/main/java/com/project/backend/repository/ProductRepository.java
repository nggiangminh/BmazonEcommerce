package com.project.backend.repository;

import com.project.backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    
    // Find all products that are not deleted
    @Query("SELECT p FROM Product p WHERE p.deletedAt IS NULL")
    Page<Product> findAllActive(Pageable pageable);
    
    // Find all products that are not deleted
    @Query("SELECT p FROM Product p WHERE p.deletedAt IS NULL")
    List<Product> findAllActive();
    
    // Find product by ID that is not deleted
    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.deletedAt IS NULL")
    Optional<Product> findByIdActive(@Param("id") UUID id);
    
    // Find products by category that are not deleted
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.deletedAt IS NULL")
    Page<Product> findByCategoryIdActive(@Param("categoryId") UUID categoryId, Pageable pageable);
    
    // Find products by category that are not deleted
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.deletedAt IS NULL")
    List<Product> findByCategoryIdActive(@Param("categoryId") UUID categoryId);
    
    // Search products by name (case insensitive) that are not deleted
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND p.deletedAt IS NULL")
    Page<Product> findByNameContainingIgnoreCaseActive(@Param("name") String name, Pageable pageable);
    
    // Search products by name (case insensitive) that are not deleted
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND p.deletedAt IS NULL")
    List<Product> findByNameContainingIgnoreCaseActive(@Param("name") String name);
    
    // Find products by price range that are not deleted
    @Query("SELECT DISTINCT p FROM Product p JOIN p.productSkus ps WHERE ps.price BETWEEN :minPrice AND :maxPrice AND p.deletedAt IS NULL")
    Page<Product> findByPriceRangeActive(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, Pageable pageable);
    
    // Find products by price range that are not deleted
    @Query("SELECT DISTINCT p FROM Product p JOIN p.productSkus ps WHERE ps.price BETWEEN :minPrice AND :maxPrice AND p.deletedAt IS NULL")
    List<Product> findByPriceRangeActive(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
    
    // Find products created after a specific date that are not deleted
    @Query("SELECT p FROM Product p WHERE p.createdAt >= :date AND p.deletedAt IS NULL")
    List<Product> findByCreatedAtAfterActive(@Param("date") LocalDateTime date);
    
    // Find products with available stock (quantity > 0) that are not deleted
    @Query("SELECT DISTINCT p FROM Product p JOIN p.productSkus ps WHERE ps.quantity > 0 AND p.deletedAt IS NULL")
    Page<Product> findAvailableProducts(Pageable pageable);
    
    // Find products with available stock (quantity > 0) that are not deleted
    @Query("SELECT DISTINCT p FROM Product p JOIN p.productSkus ps WHERE ps.quantity > 0 AND p.deletedAt IS NULL")
    List<Product> findAvailableProducts();
    
    // Count active products
    @Query("SELECT COUNT(p) FROM Product p WHERE p.deletedAt IS NULL")
    long countActive();
    
    // Count products by category that are not deleted
    @Query("SELECT COUNT(p) FROM Product p WHERE p.category.id = :categoryId AND p.deletedAt IS NULL")
    long countByCategoryIdActive(@Param("categoryId") UUID categoryId);
    
    // Admin specific queries
    @Query("SELECT p FROM Product p")
    Page<Product> findAllIncludingDeleted(Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findByIdIncludingDeleted(@Param("id") UUID id);
    
    @Query("SELECT p FROM Product p WHERE p.deletedAt IS NOT NULL")
    Page<Product> findAllDeleted(Pageable pageable);
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.deletedAt IS NOT NULL")
    long countDeleted();
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.deletedAt IS NULL")
    long countAvailable();
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.deletedAt IS NULL AND NOT EXISTS (SELECT ps FROM ProductSku ps WHERE ps.product = p AND ps.quantity > 0 AND ps.deletedAt IS NULL)")
    long countOutOfStock();
    
    // User specific queries
    @Query(value = "SELECT * FROM products WHERE deleted_at IS NULL ORDER BY created_at DESC LIMIT :limit", nativeQuery = true)
    List<Product> findFeaturedProducts(@Param("limit") int limit);
    
    @Query(value = "SELECT * FROM products WHERE deleted_at IS NULL ORDER BY created_at DESC LIMIT :limit", nativeQuery = true)
    List<Product> findTrendingProducts(@Param("limit") int limit);
    
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.id != :productId AND p.deletedAt IS NULL")
    List<Product> findSimilarProducts(@Param("categoryId") UUID categoryId, @Param("productId") UUID productId);
    
    @Query(value = "SELECT * FROM products WHERE deleted_at IS NULL ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Product> findRandomProducts(@Param("limit") int limit);
}
