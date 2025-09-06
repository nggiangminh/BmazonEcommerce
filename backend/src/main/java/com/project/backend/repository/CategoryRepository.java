package com.project.backend.repository;

import com.project.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    
    // Find all categories that are not deleted
    @Query("SELECT c FROM Category c WHERE c.deletedAt IS NULL")
    List<Category> findAllActive();
    
    // Find category by ID that is not deleted
    @Query("SELECT c FROM Category c WHERE c.id = :id AND c.deletedAt IS NULL")
    Optional<Category> findByIdActive(@Param("id") UUID id);
    
    // Find category by name that is not deleted
    @Query("SELECT c FROM Category c WHERE c.name = :name AND c.deletedAt IS NULL")
    Optional<Category> findByNameActive(@Param("name") String name);
    
    // Search categories by name (case insensitive) that are not deleted
    @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')) AND c.deletedAt IS NULL")
    List<Category> findByNameContainingIgnoreCaseActive(@Param("name") String name);
    
    // Count active categories
    @Query("SELECT COUNT(c) FROM Category c WHERE c.deletedAt IS NULL")
    long countActive();
    
    // Check if category name exists (excluding deleted)
    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.name = :name AND c.deletedAt IS NULL")
    boolean existsByNameActive(@Param("name") String name);
    
    // Check if category name exists for update (excluding deleted and current category)
    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.name = :name AND c.id != :id AND c.deletedAt IS NULL")
    boolean existsByNameAndIdNotActive(@Param("name") String name, @Param("id") UUID id);
}
