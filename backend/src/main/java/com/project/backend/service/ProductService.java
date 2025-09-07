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
    
    // Admin specific operations
    Page<Product> getAllProductsIncludingDeleted(Pageable pageable);
    Optional<Product> getProductByIdIncludingDeleted(UUID id);
    Page<Product> getDeletedProducts(Pageable pageable);
    Optional<Product> restoreProduct(UUID id);
    long getDeletedProductCount();
    long getAvailableProductCount();
    long getOutOfStockProductCount();
    boolean existsByIdIncludingDeleted(UUID id);
    
    // Bulk operations
    BulkOperationResult bulkDeleteProducts(List<UUID> productIds);
    BulkOperationResult bulkRestoreProducts(List<UUID> productIds);
    
    // User specific operations
    List<Product> getFeaturedProducts(int limit);
    List<Product> getTrendingProducts(int limit);
    List<Product> getSimilarProducts(UUID productId, int limit);
    List<Product> getProductRecommendations(int limit);
    ProductFilters getAvailableFilters();
    
    // Validation
    boolean existsById(UUID id);
    boolean isProductAvailable(UUID id);
    
    // Inner classes
    class BulkOperationResult {
        private int successCount;
        private int failureCount;
        private List<String> errors;
        
        public BulkOperationResult(int successCount, int failureCount, List<String> errors) {
            this.successCount = successCount;
            this.failureCount = failureCount;
            this.errors = errors;
        }
        
        public int getSuccessCount() { return successCount; }
        public int getFailureCount() { return failureCount; }
        public List<String> getErrors() { return errors; }
    }
    
    class ProductFilters {
        private List<String> categories;
        private List<String> priceRanges;
        private List<String> sizes;
        private List<String> colors;
        
        public ProductFilters(List<String> categories, List<String> priceRanges, List<String> sizes, List<String> colors) {
            this.categories = categories;
            this.priceRanges = priceRanges;
            this.sizes = sizes;
            this.colors = colors;
        }
        
        public List<String> getCategories() { return categories; }
        public List<String> getPriceRanges() { return priceRanges; }
        public List<String> getSizes() { return sizes; }
        public List<String> getColors() { return colors; }
    }
}
