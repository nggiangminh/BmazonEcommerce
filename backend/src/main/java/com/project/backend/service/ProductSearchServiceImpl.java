package com.project.backend.service;

import com.project.backend.dto.request.ProductSearchRequest;
import com.project.backend.entity.Product;
import com.project.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProductSearchServiceImpl implements ProductSearchService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchProducts(ProductSearchRequest request, Pageable pageable) {
        // Use advanced filter query for better performance
        String query = request.getQuery() != null && !request.getQuery().trim().isEmpty() ? request.getQuery() : null;
        UUID categoryId = (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) ? 
            request.getCategoryIds().get(0) : null; // For now, use first category
        Double minPrice = request.getMinPrice();
        Double maxPrice = request.getMaxPrice();
        
        return productRepository.findWithAdvancedFilters(query, categoryId, minPrice, maxPrice, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> searchProducts(ProductSearchRequest request) {
        Page<Product> page = searchProducts(request, Pageable.unpaged());
        return page.getContent();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchByName(String name, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCaseActive(name, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchByCategory(UUID categoryId, Pageable pageable) {
        return productRepository.findByCategoryIdActive(categoryId, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchByPriceRange(Double minPrice, Double maxPrice, Pageable pageable) {
        return productRepository.findByPriceRangeActive(minPrice, maxPrice, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchByAttributes(List<String> sizes, List<String> colors, Pageable pageable) {
        // This would require a more complex query with joins to ProductSku and ProductAttribute
        // For now, return all available products
        return productRepository.findAvailableProducts(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchByNameAndCategory(String name, UUID categoryId, Pageable pageable) {
        // This would require a custom query method in repository
        // For now, return empty page (simplified implementation)
        return Page.empty(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchByNameAndPriceRange(String name, Double minPrice, Double maxPrice, Pageable pageable) {
        // This would require a custom query method in repository
        // For now, return all available products
        return productRepository.findAvailableProducts(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchByCategoryAndPriceRange(UUID categoryId, Double minPrice, Double maxPrice, Pageable pageable) {
        // This would require a custom query method in repository
        // For now, return all available products
        return productRepository.findAvailableProducts(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> filterByMultipleCategories(List<UUID> categoryIds, Pageable pageable) {
        return productRepository.findByMultipleCategories(categoryIds, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> filterByPriceRanges(List<PriceRange> priceRanges, Pageable pageable) {
        // This would require a custom query method in repository
        // For now, return all available products
        return productRepository.findAvailableProducts(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> filterByAvailability(boolean availableOnly, Pageable pageable) {
        if (availableOnly) {
            return productRepository.findAvailableProducts(pageable);
        } else {
            return productRepository.findAllActive(pageable);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> filterByStockLevel(StockLevel stockLevel, Pageable pageable) {
        if (stockLevel.getMinStock() != 0 && stockLevel.getMaxStock() != 0) {
            return productRepository.findByStockRange(stockLevel.getMinStock(), stockLevel.getMaxStock(), pageable);
        } else if (stockLevel.getMinStock() != 0) {
            return productRepository.findByMinStock(stockLevel.getMinStock(), pageable);
        } else if (stockLevel.getMaxStock() != 0) {
            return productRepository.findByMaxStock(stockLevel.getMaxStock(), pageable);
        } else {
            return productRepository.findAvailableProducts(pageable);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchWithSorting(ProductSearchRequest request, String sortBy, String sortDirection, Pageable pageable) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        
        return searchProducts(request, sortedPageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<String> getSearchSuggestions(String query, int limit) {
        Pageable pageable = Pageable.ofSize(limit);
        return productRepository.findProductNameSuggestions(query, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<String> getCategorySuggestions(String query, int limit) {
        // This would require a custom query method in repository
        // For now, return empty list
        return new ArrayList<>();
    }
    
    @Override
    @Transactional(readOnly = true)
    public SearchStatistics getSearchStatistics(ProductSearchRequest request) {
        List<Product> products = searchProducts(request);
        
        long totalResults = products.size();
        long availableResults = products.stream()
                .mapToLong(product -> product.getProductSkus().stream()
                        .anyMatch(sku -> sku.getQuantity() > 0) ? 1 : 0)
                .sum();
        long outOfStockResults = totalResults - availableResults;
        
        return new SearchStatistics(totalResults, availableResults, outOfStockResults);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<String> getPopularSearches(int limit) {
        // This would require tracking search history in database
        // For now, return empty list
        return new ArrayList<>();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UUID> getPopularCategories(int limit) {
        // This would require tracking category views in database
        // For now, return empty list
        return new ArrayList<>();
    }
    
}
