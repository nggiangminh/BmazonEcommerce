package com.project.backend.service;

import com.project.backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    
    // Basic CRUD operations
    List<Product> getAllProducts();
    Page<Product> getAllProducts(Pageable pageable);
    Optional<Product> getProductById(UUID id);
    Product createProduct(Product product);
    Product updateProduct(UUID id, Product product);
    void deleteProduct(UUID id);
    void softDeleteProduct(UUID id);
    
    // Search and filter operations
    List<Product> searchProductsByName(String name);
    Page<Product> searchProductsByName(String name, Pageable pageable);
    List<Product> getProductsByCategory(UUID categoryId);
    Page<Product> getProductsByCategory(UUID categoryId, Pageable pageable);
    List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice);
    Page<Product> getProductsByPriceRange(Double minPrice, Double maxPrice, Pageable pageable);
    List<Product> getAvailableProducts();
    Page<Product> getAvailableProducts(Pageable pageable);
    List<Product> getRecentProducts(int limit);
    
    // Statistics
    long getTotalProductCount();
    long getProductCountByCategory(UUID categoryId);
    
    // Validation
    boolean existsById(UUID id);
    boolean isProductAvailable(UUID id);
}
