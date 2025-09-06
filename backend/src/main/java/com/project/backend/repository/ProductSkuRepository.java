package com.project.backend.repository;

import com.project.backend.entity.ProductSku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductSkuRepository extends JpaRepository<ProductSku, UUID> {
    
    // Find all SKUs that are not deleted
    @Query("SELECT ps FROM ProductSku ps WHERE ps.deletedAt IS NULL")
    List<ProductSku> findAllActive();
    
    // Find SKU by ID that is not deleted
    @Query("SELECT ps FROM ProductSku ps WHERE ps.id = :id AND ps.deletedAt IS NULL")
    Optional<ProductSku> findByIdActive(@Param("id") UUID id);
    
    // Find SKUs by product ID that are not deleted
    @Query("SELECT ps FROM ProductSku ps WHERE ps.product.id = :productId AND ps.deletedAt IS NULL")
    List<ProductSku> findByProductIdActive(@Param("productId") UUID productId);
    
    // Find SKU by SKU code that is not deleted
    @Query("SELECT ps FROM ProductSku ps WHERE ps.sku = :sku AND ps.deletedAt IS NULL")
    Optional<ProductSku> findBySkuActive(@Param("sku") String sku);
    
    // Find SKUs by product ID and size that are not deleted
    @Query("SELECT ps FROM ProductSku ps WHERE ps.product.id = :productId AND ps.sizeAttribute.id = :sizeId AND ps.deletedAt IS NULL")
    List<ProductSku> findByProductIdAndSizeActive(@Param("productId") UUID productId, @Param("sizeId") UUID sizeId);
    
    // Find SKUs by product ID and color that are not deleted
    @Query("SELECT ps FROM ProductSku ps WHERE ps.product.id = :productId AND ps.colorAttribute.id = :colorId AND ps.deletedAt IS NULL")
    List<ProductSku> findByProductIdAndColorActive(@Param("productId") UUID productId, @Param("colorId") UUID colorId);
    
    // Find SKUs by product ID, size and color that are not deleted
    @Query("SELECT ps FROM ProductSku ps WHERE ps.product.id = :productId AND ps.sizeAttribute.id = :sizeId AND ps.colorAttribute.id = :colorId AND ps.deletedAt IS NULL")
    Optional<ProductSku> findByProductIdAndSizeAndColorActive(@Param("productId") UUID productId, @Param("sizeId") UUID sizeId, @Param("colorId") UUID colorId);
    
    // Find SKUs with available stock (quantity > 0) that are not deleted
    @Query("SELECT ps FROM ProductSku ps WHERE ps.quantity > 0 AND ps.deletedAt IS NULL")
    List<ProductSku> findAvailableSkus();
    
    // Find SKUs by product ID with available stock that are not deleted
    @Query("SELECT ps FROM ProductSku ps WHERE ps.product.id = :productId AND ps.quantity > 0 AND ps.deletedAt IS NULL")
    List<ProductSku> findAvailableSkusByProductId(@Param("productId") UUID productId);
    
    // Check if SKU code exists (excluding deleted)
    @Query("SELECT COUNT(ps) > 0 FROM ProductSku ps WHERE ps.sku = :sku AND ps.deletedAt IS NULL")
    boolean existsBySkuActive(@Param("sku") String sku);
    
    // Check if SKU code exists for update (excluding deleted and current SKU)
    @Query("SELECT COUNT(ps) > 0 FROM ProductSku ps WHERE ps.sku = :sku AND ps.id != :id AND ps.deletedAt IS NULL")
    boolean existsBySkuAndIdNotActive(@Param("sku") String sku, @Param("id") UUID id);
    
    // Count active SKUs by product ID
    @Query("SELECT COUNT(ps) FROM ProductSku ps WHERE ps.product.id = :productId AND ps.deletedAt IS NULL")
    long countByProductIdActive(@Param("productId") UUID productId);
}
