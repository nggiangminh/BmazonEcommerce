package com.project.backend.controller;

import com.project.backend.dto.request.CreateProductRequest;
import com.project.backend.dto.request.UpdateProductRequest;
import com.project.backend.dto.response.ProductDTO;
import com.project.backend.entity.Product;
import com.project.backend.mapper.ProductMapper;
import com.project.backend.service.CategoryService;
import com.project.backend.service.ProductService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "APIs for managing products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ProductMapper productMapper;
    
    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve all active products with pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Product> products = productService.getAllProducts(pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/all")
    @Operation(summary = "Get all products without pagination", description = "Retrieve all active products as a list")
    public ResponseEntity<List<ProductDTO>> getAllProductsList() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOs = productMapper.toDTOList(products);
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            ProductDTO productDTO = productMapper.toDTO(product.get());
            return ResponseEntity.ok(productDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    @Operation(summary = "Create new product", description = "Create a new product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody CreateProductRequest request) {
        // Validate category exists
        if (!categoryService.existsById(request.getCategoryId())) {
            return ResponseEntity.badRequest().build();
        }
        
        Product product = productMapper.toEntity(request);
        Product savedProduct = productService.createProduct(product);
        ProductDTO productDTO = productMapper.toDTO(savedProduct);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Update an existing product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID id, @Valid @RequestBody UpdateProductRequest request) {
        // Validate category exists if provided
        if (request.getCategoryId() != null && !categoryService.existsById(request.getCategoryId())) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<Product> existingProduct = productService.getProductById(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = productMapper.toEntity(request, existingProduct.get());
            Product savedProduct = productService.updateProduct(id, updatedProduct);
            ProductDTO productDTO = productMapper.toDTO(savedProduct);
            return ResponseEntity.ok(productDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Permanently delete a product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        if (productService.existsById(id)) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}/soft")
    @Operation(summary = "Soft delete product", description = "Soft delete a product (mark as deleted)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product soft deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> softDeleteProduct(@PathVariable UUID id) {
        if (productService.existsById(id)) {
            productService.softDeleteProduct(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search products by name", description = "Search products by name with pagination")
    public ResponseEntity<Page<ProductDTO>> searchProducts(
            @Parameter(description = "Search term") @RequestParam String name,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Product> products = productService.searchProductsByName(name, pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get products by category", description = "Get products filtered by category with pagination")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategory(
            @PathVariable UUID categoryId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Product> products = productService.getProductsByCategory(categoryId, pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/available")
    @Operation(summary = "Get available products", description = "Get products with available stock")
    public ResponseEntity<Page<ProductDTO>> getAvailableProducts(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Product> products = productService.getAvailableProducts(pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/recent")
    @Operation(summary = "Get recent products", description = "Get recently added products")
    public ResponseEntity<List<ProductDTO>> getRecentProducts(
            @Parameter(description = "Number of recent products to return") @RequestParam(defaultValue = "10") int limit) {
        
        List<Product> products = productService.getRecentProducts(limit);
        List<ProductDTO> productDTOs = productMapper.toDTOList(products);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/stats")
    @Operation(summary = "Get product statistics", description = "Get product count statistics")
    public ResponseEntity<ProductStats> getProductStats() {
        long totalProducts = productService.getTotalProductCount();
        return ResponseEntity.ok(new ProductStats(totalProducts));
    }
    
    // Inner class for statistics
    public static class ProductStats {
        private long totalProducts;
        
        public ProductStats(long totalProducts) {
            this.totalProducts = totalProducts;
        }
        
        public long getTotalProducts() {
            return totalProducts;
        }
        
        public void setTotalProducts(long totalProducts) {
            this.totalProducts = totalProducts;
        }
    }
}
