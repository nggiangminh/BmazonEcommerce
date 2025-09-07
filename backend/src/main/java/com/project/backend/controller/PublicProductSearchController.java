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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/search")
@Tag(name = "Public Product Search & Filter", description = "Public APIs for product search and filtering without authentication")
public class PublicProductSearchController {
    
    @Autowired
    private ProductSearchService productSearchService;
    
    @Autowired
    private ProductMapper productMapper;
    
    @PostMapping("/products")
    @Operation(summary = "Advanced product search (Public)", description = "Search products with multiple criteria without authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search completed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid search parameters"),
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
    @Operation(summary = "Search products by name (Public)", description = "Search products by product name without authentication")
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
    @Operation(summary = "Search products by category (Public)", description = "Search products by category ID without authentication")
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
    @Operation(summary = "Search products by price range (Public)", description = "Search products within a price range without authentication")
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
    
    @GetMapping("/products/available")
    @Operation(summary = "Filter products by availability (Public)", description = "Filter products by availability status without authentication")
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
    
    @GetMapping("/suggestions")
    @Operation(summary = "Get search suggestions (Public)", description = "Get product name suggestions for autocomplete without authentication")
    public ResponseEntity<SearchSuggestions> getSearchSuggestions(
            @Parameter(description = "Search query") @RequestParam String query,
            @Parameter(description = "Number of suggestions") @RequestParam(defaultValue = "10") int limit) {
        
        List<String> suggestions = productSearchService.getSearchSuggestions(query, limit);
        SearchSuggestions response = new SearchSuggestions(suggestions);
        
        return ResponseEntity.ok(response);
    }
    
    // Inner class for response
    public static class SearchSuggestions {
        private List<String> suggestions;
        
        public SearchSuggestions(List<String> suggestions) {
            this.suggestions = suggestions;
        }
        
        public List<String> getSuggestions() { return suggestions; }
        public void setSuggestions(List<String> suggestions) { this.suggestions = suggestions; }
    }
}
