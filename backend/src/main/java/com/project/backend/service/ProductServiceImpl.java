package com.project.backend.service;

import com.project.backend.entity.Product;
import com.project.backend.exception.ResourceNotFoundException;
import com.project.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAllActive();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAllActive(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(UUID id) {
        return productRepository.findByIdActive(id);
    }
    
    @Override
    public Product createProduct(Product product) {
        if (product.getCreatedAt() == null) {
            product.setCreatedAt(LocalDateTime.now());
        }
        return productRepository.save(product);
    }
    
    @Override
    public Product updateProduct(UUID id, Product product) {
        Product existingProduct = productRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        // Update fields
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setSummary(product.getSummary());
        existingProduct.setCover(product.getCover());
        existingProduct.setCategory(product.getCategory());
        
        return productRepository.save(existingProduct);
    }
    
    @Override
    public void deleteProduct(UUID id) {
        Product product = productRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }
    
    @Override
    public void softDeleteProduct(UUID id) {
        Product product = productRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.softDelete();
        productRepository.save(product);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCaseActive(name);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchProductsByName(String name, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCaseActive(name, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByCategory(UUID categoryId) {
        return productRepository.findByCategoryIdActive(categoryId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> getProductsByCategory(UUID categoryId, Pageable pageable) {
        return productRepository.findByCategoryIdActive(categoryId, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceRangeActive(minPrice, maxPrice);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> getProductsByPriceRange(Double minPrice, Double maxPrice, Pageable pageable) {
        return productRepository.findByPriceRangeActive(minPrice, maxPrice, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> getAvailableProducts() {
        return productRepository.findAvailableProducts();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> getAvailableProducts(Pageable pageable) {
        return productRepository.findAvailableProducts(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> getRecentProducts(int limit) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30); // Last 30 days
        return productRepository.findByCreatedAtAfterActive(cutoffDate)
                .stream()
                .limit(limit)
                .toList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getTotalProductCount() {
        return productRepository.countActive();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getProductCountByCategory(UUID categoryId) {
        return productRepository.countByCategoryIdActive(categoryId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return productRepository.findByIdActive(id).isPresent();
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isProductAvailable(UUID id) {
        return productRepository.findAvailableProducts()
                .stream()
                .anyMatch(product -> product.getId().equals(id));
    }
    
    // Admin specific operations
    @Override
    @Transactional(readOnly = true)
    public Page<Product> getAllProductsIncludingDeleted(Pageable pageable) {
        return productRepository.findAllIncludingDeleted(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductByIdIncludingDeleted(UUID id) {
        return productRepository.findByIdIncludingDeleted(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> getDeletedProducts(Pageable pageable) {
        return productRepository.findAllDeleted(pageable);
    }
    
    @Override
    public Optional<Product> restoreProduct(UUID id) {
        Optional<Product> product = productRepository.findByIdIncludingDeleted(id);
        if (product.isPresent() && product.get().isDeleted()) {
            product.get().setDeletedAt(null);
            return Optional.of(productRepository.save(product.get()));
        }
        return Optional.empty();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getDeletedProductCount() {
        return productRepository.countDeleted();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getAvailableProductCount() {
        return productRepository.countAvailable();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getOutOfStockProductCount() {
        return productRepository.countOutOfStock();
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByIdIncludingDeleted(UUID id) {
        return productRepository.findByIdIncludingDeleted(id).isPresent();
    }
    
    // Bulk operations
    @Override
    public BulkOperationResult bulkDeleteProducts(List<UUID> productIds) {
        int successCount = 0;
        int failureCount = 0;
        List<String> errors = new ArrayList<>();
        
        for (UUID id : productIds) {
            try {
                if (existsById(id)) {
                    softDeleteProduct(id);
                    successCount++;
                } else {
                    failureCount++;
                    errors.add("Product with ID " + id + " not found");
                }
            } catch (Exception e) {
                failureCount++;
                errors.add("Failed to delete product with ID " + id + ": " + e.getMessage());
            }
        }
        
        return new BulkOperationResult(successCount, failureCount, errors);
    }
    
    @Override
    public BulkOperationResult bulkRestoreProducts(List<UUID> productIds) {
        int successCount = 0;
        int failureCount = 0;
        List<String> errors = new ArrayList<>();
        
        for (UUID id : productIds) {
            try {
                Optional<Product> restored = restoreProduct(id);
                if (restored.isPresent()) {
                    successCount++;
                } else {
                    failureCount++;
                    errors.add("Product with ID " + id + " not found or not deleted");
                }
            } catch (Exception e) {
                failureCount++;
                errors.add("Failed to restore product with ID " + id + ": " + e.getMessage());
            }
        }
        
        return new BulkOperationResult(successCount, failureCount, errors);
    }
    
    // User specific operations
    @Override
    @Transactional(readOnly = true)
    public List<Product> getFeaturedProducts(int limit) {
        return productRepository.findFeaturedProducts(limit)
                .stream()
                .limit(limit)
                .toList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> getTrendingProducts(int limit) {
        return productRepository.findTrendingProducts(limit)
                .stream()
                .limit(limit)
                .toList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> getSimilarProducts(UUID productId, int limit) {
        Optional<Product> product = getProductById(productId);
        if (product.isPresent() && product.get().getCategory() != null) {
            return productRepository.findSimilarProducts(
                product.get().getCategory().getId(), 
                productId
            ).stream().limit(limit).toList();
        }
        return new ArrayList<>();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductRecommendations(int limit) {
        return productRepository.findRandomProducts(limit)
                .stream()
                .limit(limit)
                .toList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public ProductFilters getAvailableFilters() {
        // This is a simplified implementation
        // In a real application, you would query the database for actual filter values
        List<String> categories = List.of("Electronics", "Clothing", "Books", "Home & Garden");
        List<String> priceRanges = List.of("0-50", "50-100", "100-200", "200-500", "500+");
        List<String> sizes = List.of("XS", "S", "M", "L", "XL", "XXL");
        List<String> colors = List.of("Red", "Blue", "Green", "Black", "White", "Yellow");
        
        return new ProductFilters(categories, priceRanges, sizes, colors);
    }
}
