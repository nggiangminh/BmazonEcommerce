package com.project.backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public class ProductSearchRequest {
    
    @Size(max = 255, message = "Search query must not exceed 255 characters")
    private String query;
    
    private List<UUID> categoryIds;
    
    @Min(value = 0, message = "Minimum price must be non-negative")
    private Double minPrice;
    
    @Min(value = 0, message = "Maximum price must be non-negative")
    private Double maxPrice;
    
    private List<String> sizes;
    
    private List<String> colors;
    
    @Min(value = 0, message = "Page number must be non-negative")
    private int page = 0;
    
    @Min(value = 1, message = "Page size must be at least 1")
    private int size = 12;
    
    private String sortBy = "name";
    
    private String sortDirection = "asc";
    
    public ProductSearchRequest() {}
    
    public String getQuery() {
        return query;
    }
    
    public void setQuery(String query) {
        this.query = query;
    }
    
    public List<UUID> getCategoryIds() {
        return categoryIds;
    }
    
    public void setCategoryIds(List<UUID> categoryIds) {
        this.categoryIds = categoryIds;
    }
    
    public Double getMinPrice() {
        return minPrice;
    }
    
    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }
    
    public Double getMaxPrice() {
        return maxPrice;
    }
    
    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
    
    public List<String> getSizes() {
        return sizes;
    }
    
    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }
    
    public List<String> getColors() {
        return colors;
    }
    
    public void setColors(List<String> colors) {
        this.colors = colors;
    }
    
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    
    public String getSortBy() {
        return sortBy;
    }
    
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    
    public String getSortDirection() {
        return sortDirection;
    }
    
    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
