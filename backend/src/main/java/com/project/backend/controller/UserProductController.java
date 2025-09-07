package com.project.backend.controller;

import com.project.backend.dto.response.ProductDTO;
import com.project.backend.entity.Product;
import com.project.backend.mapper.ProductMapper;
import com.project.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/products")
@Tag(name = "User Product Management", description = "User APIs for browsing and viewing products")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class UserProductController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductMapper productMapper;
    
    @GetMapping
    @Operation(summary = "Browse products (User)", description = "Browse available products with pagination and sorting")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<ProductDTO>> browseProducts(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Product> products = productService.getAvailableProducts(pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "View product details (User)", description = "View detailed information of a specific product")
    public ResponseEntity<ProductDTO> viewProductDetails(@PathVariable UUID id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent() && productService.isProductAvailable(id)) {
            ProductDTO productDTO = productMapper.toDTO(product.get());
            return ResponseEntity.ok(productDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search products (User)", description = "Search products by name with advanced filtering")
    public ResponseEntity<Page<ProductDTO>> searchProducts(
            @Parameter(description = "Search term") @RequestParam String query,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Product> products = productService.searchProductsByName(query, pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Browse products by category (User)", description = "Browse products filtered by category")
    public ResponseEntity<Page<ProductDTO>> browseProductsByCategory(
            @PathVariable UUID categoryId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Product> products = productService.getProductsByCategory(categoryId, pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/featured")
    @Operation(summary = "Get featured products (User)", description = "Get featured/recommended products")
    public ResponseEntity<List<ProductDTO>> getFeaturedProducts(
            @Parameter(description = "Number of featured products to return") @RequestParam(defaultValue = "8") int limit) {
        
        List<Product> products = productService.getFeaturedProducts(limit);
        List<ProductDTO> productDTOs = productMapper.toDTOList(products);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/recent")
    @Operation(summary = "Get recent products (User)", description = "Get recently added products")
    public ResponseEntity<List<ProductDTO>> getRecentProducts(
            @Parameter(description = "Number of recent products to return") @RequestParam(defaultValue = "10") int limit) {
        
        List<Product> products = productService.getRecentProducts(limit);
        List<ProductDTO> productDTOs = productMapper.toDTOList(products);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/trending")
    @Operation(summary = "Get trending products (User)", description = "Get trending/popular products")
    public ResponseEntity<List<ProductDTO>> getTrendingProducts(
            @Parameter(description = "Number of trending products to return") @RequestParam(defaultValue = "8") int limit) {
        
        List<Product> products = productService.getTrendingProducts(limit);
        List<ProductDTO> productDTOs = productMapper.toDTOList(products);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/price-range")
    @Operation(summary = "Filter products by price range (User)", description = "Filter products within a specific price range")
    public ResponseEntity<Page<ProductDTO>> getProductsByPriceRange(
            @Parameter(description = "Minimum price") @RequestParam Double minPrice,
            @Parameter(description = "Maximum price") @RequestParam Double maxPrice,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "price") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice, pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/similar/{productId}")
    @Operation(summary = "Get similar products (User)", description = "Get products similar to the specified product")
    public ResponseEntity<List<ProductDTO>> getSimilarProducts(
            @PathVariable UUID productId,
            @Parameter(description = "Number of similar products to return") @RequestParam(defaultValue = "4") int limit) {
        
        List<Product> products = productService.getSimilarProducts(productId, limit);
        List<ProductDTO> productDTOs = productMapper.toDTOList(products);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/recommendations")
    @Operation(summary = "Get product recommendations (User)", description = "Get personalized product recommendations")
    public ResponseEntity<List<ProductDTO>> getProductRecommendations(
            @Parameter(description = "Number of recommendations to return") @RequestParam(defaultValue = "6") int limit) {
        
        List<Product> products = productService.getProductRecommendations(limit);
        List<ProductDTO> productDTOs = productMapper.toDTOList(products);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/filters")
    @Operation(summary = "Get available filters (User)", description = "Get available filter options for products")
    public ResponseEntity<ProductService.ProductFilters> getAvailableFilters() {
        ProductService.ProductFilters filters = productService.getAvailableFilters();
        return ResponseEntity.ok(filters);
    }
    
    // Inner class for filters
    public static class ProductFilters {
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
        
        // Getters and setters
        public List<String> getCategories() { return categories; }
        public void setCategories(List<String> categories) { this.categories = categories; }
        
        public List<String> getPriceRanges() { return priceRanges; }
        public void setPriceRanges(List<String> priceRanges) { this.priceRanges = priceRanges; }
        
        public List<String> getSizes() { return sizes; }
        public void setSizes(List<String> sizes) { this.sizes = sizes; }
        
        public List<String> getColors() { return colors; }
        public void setColors(List<String> colors) { this.colors = colors; }
    }
}
