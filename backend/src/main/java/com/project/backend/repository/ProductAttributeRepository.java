package com.project.backend.repository;

import com.project.backend.entity.ProductAttribute;
import com.project.backend.enums.ProductAttributeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, UUID> {
    
    // Find all attributes that are not deleted
    @Query("SELECT pa FROM ProductAttribute pa WHERE pa.deletedAt IS NULL")
    List<ProductAttribute> findAllActive();
    
    // Find attribute by ID that is not deleted
    @Query("SELECT pa FROM ProductAttribute pa WHERE pa.id = :id AND pa.deletedAt IS NULL")
    Optional<ProductAttribute> findByIdActive(@Param("id") UUID id);
    
    // Find attributes by type that are not deleted
    @Query("SELECT pa FROM ProductAttribute pa WHERE pa.type = :type AND pa.deletedAt IS NULL")
    List<ProductAttribute> findByTypeActive(@Param("type") ProductAttributeType type);
    
    // Find attribute by type and value that is not deleted
    @Query("SELECT pa FROM ProductAttribute pa WHERE pa.type = :type AND pa.value = :value AND pa.deletedAt IS NULL")
    Optional<ProductAttribute> findByTypeAndValueActive(@Param("type") ProductAttributeType type, @Param("value") String value);
    
    // Find attributes by type and value containing (case insensitive) that are not deleted
    @Query("SELECT pa FROM ProductAttribute pa WHERE pa.type = :type AND LOWER(pa.value) LIKE LOWER(CONCAT('%', :value, '%')) AND pa.deletedAt IS NULL")
    List<ProductAttribute> findByTypeAndValueContainingIgnoreCaseActive(@Param("type") ProductAttributeType type, @Param("value") String value);
    
    // Find all color attributes that are not deleted
    @Query("SELECT pa FROM ProductAttribute pa WHERE pa.type = 'color' AND pa.deletedAt IS NULL")
    List<ProductAttribute> findColorAttributesActive();
    
    // Find all size attributes that are not deleted
    @Query("SELECT pa FROM ProductAttribute pa WHERE pa.type = 'size' AND pa.deletedAt IS NULL")
    List<ProductAttribute> findSizeAttributesActive();
    
    // Check if attribute with type and value exists (excluding deleted)
    @Query("SELECT COUNT(pa) > 0 FROM ProductAttribute pa WHERE pa.type = :type AND pa.value = :value AND pa.deletedAt IS NULL")
    boolean existsByTypeAndValueActive(@Param("type") ProductAttributeType type, @Param("value") String value);
    
    // Check if attribute with type and value exists for update (excluding deleted and current attribute)
    @Query("SELECT COUNT(pa) > 0 FROM ProductAttribute pa WHERE pa.type = :type AND pa.value = :value AND pa.id != :id AND pa.deletedAt IS NULL")
    boolean existsByTypeAndValueAndIdNotActive(@Param("type") ProductAttributeType type, @Param("value") String value, @Param("id") UUID id);
    
    // Count active attributes by type
    @Query("SELECT COUNT(pa) FROM ProductAttribute pa WHERE pa.type = :type AND pa.deletedAt IS NULL")
    long countByTypeActive(@Param("type") ProductAttributeType type);
}
