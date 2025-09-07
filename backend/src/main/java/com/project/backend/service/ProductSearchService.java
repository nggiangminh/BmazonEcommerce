package com.project.backend.service;

import com.project.backend.dto.request.ProductSearchRequest;
import com.project.backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductSearchService {
    
    // Advanced search with multiple criteria
    Page<Product> searchProducts(ProductSearchRequest request, Pageable pageable);
    List<Product> searchProducts(ProductSearchRequest request);
    
    // Individual search methods
    Page<Product> searchByName(String name, Pageable pageable);
    Page<Product> searchByCategory(UUID categoryId, Pageable pageable);
    Page<Product> searchByPriceRange(Double minPrice, Double maxPrice, Pageable pageable);
    Page<Product> searchByAttributes(List<String> sizes, List<String> colors, Pageable pageable);
    
    // Combined search methods
    Page<Product> searchByNameAndCategory(String name, UUID categoryId, Pageable pageable);
    Page<Product> searchByNameAndPriceRange(String name, Double minPrice, Double maxPrice, Pageable pageable);
    Page<Product> searchByCategoryAndPriceRange(UUID categoryId, Double minPrice, Double maxPrice, Pageable pageable);
    
    // Advanced filtering
    Page<Product> filterByMultipleCategories(List<UUID> categoryIds, Pageable pageable);
    Page<Product> filterByPriceRanges(List<PriceRange> priceRanges, Pageable pageable);
    Page<Product> filterByAvailability(boolean availableOnly, Pageable pageable);
    Page<Product> filterByStockLevel(StockLevel stockLevel, Pageable pageable);
    
    // Sorting options
    Page<Product> searchWithSorting(ProductSearchRequest request, String sortBy, String sortDirection, Pageable pageable);
    
    // Search suggestions and autocomplete
    List<String> getSearchSuggestions(String query, int limit);
    List<String> getCategorySuggestions(String query, int limit);
    
    // Search statistics
    SearchStatistics getSearchStatistics(ProductSearchRequest request);
    
    // Popular searches
    List<String> getPopularSearches(int limit);
    List<UUID> getPopularCategories(int limit);
    
    // Inner classes
    class PriceRange {
        private Double minPrice;
        private Double maxPrice;
        
        public PriceRange() {}
        
        public PriceRange(Double minPrice, Double maxPrice) {
            this.minPrice = minPrice;
            this.maxPrice = maxPrice;
        }
        
        public Double getMinPrice() { return minPrice; }
        public void setMinPrice(Double minPrice) { this.minPrice = minPrice; }
        
        public Double getMaxPrice() { return maxPrice; }
        public void setMaxPrice(Double maxPrice) { this.maxPrice = maxPrice; }
    }
    
    class StockLevel {
        private int minStock;
        private int maxStock;
        
        public StockLevel() {}
        
        public StockLevel(int minStock, int maxStock) {
            this.minStock = minStock;
            this.maxStock = maxStock;
        }
        
        public int getMinStock() { return minStock; }
        public void setMinStock(int minStock) { this.minStock = minStock; }
        
        public int getMaxStock() { return maxStock; }
        public void setMaxStock(int maxStock) { this.maxStock = maxStock; }
    }
    
    class SearchStatistics {
        private long totalResults;
        private long availableResults;
        private long outOfStockResults;
        private List<CategoryCount> categoryCounts;
        private List<PriceRangeCount> priceRangeCounts;
        
        public SearchStatistics() {}
        
        public SearchStatistics(long totalResults, long availableResults, long outOfStockResults) {
            this.totalResults = totalResults;
            this.availableResults = availableResults;
            this.outOfStockResults = outOfStockResults;
        }
        
        // Getters and setters
        public long getTotalResults() { return totalResults; }
        public void setTotalResults(long totalResults) { this.totalResults = totalResults; }
        
        public long getAvailableResults() { return availableResults; }
        public void setAvailableResults(long availableResults) { this.availableResults = availableResults; }
        
        public long getOutOfStockResults() { return outOfStockResults; }
        public void setOutOfStockResults(long outOfStockResults) { this.outOfStockResults = outOfStockResults; }
        
        public List<CategoryCount> getCategoryCounts() { return categoryCounts; }
        public void setCategoryCounts(List<CategoryCount> categoryCounts) { this.categoryCounts = categoryCounts; }
        
        public List<PriceRangeCount> getPriceRangeCounts() { return priceRangeCounts; }
        public void setPriceRangeCounts(List<PriceRangeCount> priceRangeCounts) { this.priceRangeCounts = priceRangeCounts; }
    }
    
    class CategoryCount {
        private UUID categoryId;
        private String categoryName;
        private long count;
        
        public CategoryCount() {}
        
        public CategoryCount(UUID categoryId, String categoryName, long count) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.count = count;
        }
        
        public UUID getCategoryId() { return categoryId; }
        public void setCategoryId(UUID categoryId) { this.categoryId = categoryId; }
        
        public String getCategoryName() { return categoryName; }
        public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
        
        public long getCount() { return count; }
        public void setCount(long count) { this.count = count; }
    }
    
    class PriceRangeCount {
        private String priceRange;
        private long count;
        
        public PriceRangeCount() {}
        
        public PriceRangeCount(String priceRange, long count) {
            this.priceRange = priceRange;
            this.count = count;
        }
        
        public String getPriceRange() { return priceRange; }
        public void setPriceRange(String priceRange) { this.priceRange = priceRange; }
        
        public long getCount() { return count; }
        public void setCount(long count) { this.count = count; }
    }
}
