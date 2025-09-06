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
}
