package com.project.backend.controller;

import com.project.backend.dto.response.ProductDTO;
import com.project.backend.entity.Product;
import com.project.backend.mapper.ProductMapper;
import com.project.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/products")
@Tag(name = "Public Product Management", description = "Public APIs for browsing products without authentication")
public class PublicProductController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductMapper productMapper;
    
    @GetMapping
    @Operation(summary = "Browse products (Public)", description = "Browse available products without authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
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
    @Operation(summary = "View product details (Public)", description = "View basic product information without authentication")
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
    @Operation(summary = "Search products (Public)", description = "Search products by name without authentication")
    public ResponseEntity<Page<ProductDTO>> searchProducts(
            @Parameter(description = "Search term") @RequestParam String query,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Product> products = productService.searchProductsByName(query, pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Browse products by category (Public)", description = "Browse products by category without authentication")
    public ResponseEntity<Page<ProductDTO>> browseProductsByCategory(
            @PathVariable UUID categoryId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Product> products = productService.getProductsByCategory(categoryId, pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/featured")
    @Operation(summary = "Get featured products (Public)", description = "Get featured products without authentication")
    public ResponseEntity<List<ProductDTO>> getFeaturedProducts(
            @Parameter(description = "Number of featured products to return") @RequestParam(defaultValue = "6") int limit) {
        
        List<Product> products = productService.getRecentProducts(limit);
        List<ProductDTO> productDTOs = productMapper.toDTOList(products);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/recent")
    @Operation(summary = "Get recent products (Public)", description = "Get recently added products without authentication")
    public ResponseEntity<List<ProductDTO>> getRecentProducts(
            @Parameter(description = "Number of recent products to return") @RequestParam(defaultValue = "8") int limit) {
        
        List<Product> products = productService.getRecentProducts(limit);
        List<ProductDTO> productDTOs = productMapper.toDTOList(products);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/trending")
    @Operation(summary = "Get trending products (Public)", description = "Get trending products without authentication")
    public ResponseEntity<List<ProductDTO>> getTrendingProducts(
            @Parameter(description = "Number of trending products to return") @RequestParam(defaultValue = "6") int limit) {
        
        List<Product> products = productService.getRecentProducts(limit);
        List<ProductDTO> productDTOs = productMapper.toDTOList(products);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/price-range")
    @Operation(summary = "Filter products by price range (Public)", description = "Filter products by price range without authentication")
    public ResponseEntity<Page<ProductDTO>> getProductsByPriceRange(
            @Parameter(description = "Minimum price") @RequestParam Double minPrice,
            @Parameter(description = "Maximum price") @RequestParam Double maxPrice,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice, pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/similar/{productId}")
    @Operation(summary = "Get similar products (Public)", description = "Get similar products without authentication")
    public ResponseEntity<List<ProductDTO>> getSimilarProducts(
            @PathVariable UUID productId,
            @Parameter(description = "Number of similar products to return") @RequestParam(defaultValue = "4") int limit) {
        
        List<Product> products = productService.getProductRecommendations(limit);
        List<ProductDTO> productDTOs = productMapper.toDTOList(products);
        
        return ResponseEntity.ok(productDTOs);
    }
}
