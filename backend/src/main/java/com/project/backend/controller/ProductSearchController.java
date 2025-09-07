package com.project.backend.controller;

import com.project.backend.dto.request.ProductSearchRequest;
import com.project.backend.dto.response.ProductDTO;
import com.project.backend.dto.response.ProductListResponse;
import com.project.backend.entity.Product;
import com.project.backend.mapper.ProductMapper;
import com.project.backend.service.ProductSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/search")
@Tag(name = "Product Search & Filter", description = "APIs for advanced product search and filtering")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class ProductSearchController {
    
    @Autowired
    private ProductSearchService productSearchService;
    
    @Autowired
    private ProductMapper productMapper;
    
    @PostMapping("/products")
    @Operation(summary = "Advanced product search", description = "Search products with multiple criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search completed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid search parameters"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductListResponse> searchProducts(
            @Valid @RequestBody ProductSearchRequest request,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Product> products = productSearchService.searchWithSorting(request, sortBy, sortDir, pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        ProductListResponse response = new ProductListResponse(productDTOs);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/products/name")
    @Operation(summary = "Search products by name", description = "Search products by product name")
    public ResponseEntity<ProductListResponse> searchByName(
            @Parameter(description = "Search query") @RequestParam String query,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Product> products = productSearchService.searchByName(query, pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        ProductListResponse response = new ProductListResponse(productDTOs);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/products/category/{categoryId}")
    @Operation(summary = "Search products by category", description = "Search products by category ID")
    public ResponseEntity<ProductListResponse> searchByCategory(
            @PathVariable UUID categoryId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Product> products = productSearchService.searchByCategory(categoryId, pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        ProductListResponse response = new ProductListResponse(productDTOs);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/products/price-range")
    @Operation(summary = "Search products by price range", description = "Search products within a price range")
    public ResponseEntity<ProductListResponse> searchByPriceRange(
            @Parameter(description = "Minimum price") @RequestParam Double minPrice,
            @Parameter(description = "Maximum price") @RequestParam Double maxPrice,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Product> products = productSearchService.searchByPriceRange(minPrice, maxPrice, pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        ProductListResponse response = new ProductListResponse(productDTOs);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/products/attributes")
    @Operation(summary = "Search products by attributes", description = "Search products by size and color attributes")
    public ResponseEntity<ProductListResponse> searchByAttributes(
            @Parameter(description = "Sizes") @RequestParam(required = false) List<String> sizes,
            @Parameter(description = "Colors") @RequestParam(required = false) List<String> colors,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Product> products = productSearchService.searchByAttributes(sizes, colors, pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        ProductListResponse response = new ProductListResponse(productDTOs);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/products/available")
    @Operation(summary = "Filter products by availability", description = "Filter products by availability status")
    public ResponseEntity<ProductListResponse> filterByAvailability(
            @Parameter(description = "Available only") @RequestParam(defaultValue = "true") boolean availableOnly,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Product> products = productSearchService.filterByAvailability(availableOnly, pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        ProductListResponse response = new ProductListResponse(productDTOs);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/products/stock")
    @Operation(summary = "Filter products by stock level", description = "Filter products by stock level")
    public ResponseEntity<ProductListResponse> filterByStockLevel(
            @Parameter(description = "Minimum stock") @RequestParam(required = false) Integer minStock,
            @Parameter(description = "Maximum stock") @RequestParam(required = false) Integer maxStock,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        ProductSearchService.StockLevel stockLevel = new ProductSearchService.StockLevel(minStock, maxStock);
        Page<Product> products = productSearchService.filterByStockLevel(stockLevel, pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        ProductListResponse response = new ProductListResponse(productDTOs);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/suggestions")
    @Operation(summary = "Get search suggestions", description = "Get product name suggestions for autocomplete")
    public ResponseEntity<SearchSuggestions> getSearchSuggestions(
            @Parameter(description = "Search query") @RequestParam String query,
            @Parameter(description = "Number of suggestions") @RequestParam(defaultValue = "10") int limit) {
        
        List<String> suggestions = productSearchService.getSearchSuggestions(query, limit);
        SearchSuggestions response = new SearchSuggestions(suggestions);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/statistics")
    @Operation(summary = "Get search statistics", description = "Get statistics for search results")
    public ResponseEntity<ProductSearchService.SearchStatistics> getSearchStatistics(
            @Valid @RequestBody ProductSearchRequest request) {
        
        ProductSearchService.SearchStatistics statistics = productSearchService.getSearchStatistics(request);
        return ResponseEntity.ok(statistics);
    }
    
    @GetMapping("/popular/searches")
    @Operation(summary = "Get popular searches", description = "Get popular search terms")
    public ResponseEntity<PopularSearches> getPopularSearches(
            @Parameter(description = "Number of popular searches") @RequestParam(defaultValue = "10") int limit) {
        
        List<String> popularSearches = productSearchService.getPopularSearches(limit);
        PopularSearches response = new PopularSearches(popularSearches);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/popular/categories")
    @Operation(summary = "Get popular categories", description = "Get popular category IDs")
    public ResponseEntity<PopularCategories> getPopularCategories(
            @Parameter(description = "Number of popular categories") @RequestParam(defaultValue = "10") int limit) {
        
        List<UUID> popularCategories = productSearchService.getPopularCategories(limit);
        PopularCategories response = new PopularCategories(popularCategories);
        
        return ResponseEntity.ok(response);
    }
    
    // Inner classes for responses
    public static class SearchSuggestions {
        private List<String> suggestions;
        
        public SearchSuggestions(List<String> suggestions) {
            this.suggestions = suggestions;
        }
        
        public List<String> getSuggestions() { return suggestions; }
        public void setSuggestions(List<String> suggestions) { this.suggestions = suggestions; }
    }
    
    public static class PopularSearches {
        private List<String> searches;
        
        public PopularSearches(List<String> searches) {
            this.searches = searches;
        }
        
        public List<String> getSearches() { return searches; }
        public void setSearches(List<String> searches) { this.searches = searches; }
    }
    
    public static class PopularCategories {
        private List<UUID> categories;
        
        public PopularCategories(List<UUID> categories) {
            this.categories = categories;
        }
        
        public List<UUID> getCategories() { return categories; }
        public void setCategories(List<UUID> categories) { this.categories = categories; }
    }
}
