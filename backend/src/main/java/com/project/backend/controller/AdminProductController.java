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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/products")
@Tag(name = "Admin Product Management", description = "Admin APIs for managing products")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ProductMapper productMapper;
    
    @GetMapping
    @Operation(summary = "Get all products (Admin)", description = "Retrieve all products including deleted ones for admin")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir,
            @Parameter(description = "Include deleted products") @RequestParam(defaultValue = "false") boolean includeDeleted) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Product> products;
        if (includeDeleted) {
            // For admin, we can get all products including deleted ones
            products = productService.getAllProductsIncludingDeleted(pageable);
        } else {
            products = productService.getAllProducts(pageable);
        }
        
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID (Admin)", description = "Retrieve a specific product by its ID including deleted ones")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID id) {
        Optional<Product> product = productService.getProductByIdIncludingDeleted(id);
        if (product.isPresent()) {
            ProductDTO productDTO = productMapper.toDTO(product.get());
            return ResponseEntity.ok(productDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    @Operation(summary = "Create new product (Admin)", description = "Create a new product with full validation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
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
    @Operation(summary = "Update product (Admin)", description = "Update an existing product with full validation")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID id, @Valid @RequestBody UpdateProductRequest request) {
        // Validate category exists if provided
        if (request.getCategoryId() != null && !categoryService.existsById(request.getCategoryId())) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<Product> existingProduct = productService.getProductByIdIncludingDeleted(id);
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
    @Operation(summary = "Permanently delete product (Admin)", description = "Permanently delete a product from database")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        if (productService.existsByIdIncludingDeleted(id)) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}/soft")
    @Operation(summary = "Soft delete product (Admin)", description = "Soft delete a product (mark as deleted)")
    public ResponseEntity<Void> softDeleteProduct(@PathVariable UUID id) {
        if (productService.existsById(id)) {
            productService.softDeleteProduct(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/restore")
    @Operation(summary = "Restore deleted product (Admin)", description = "Restore a soft-deleted product")
    public ResponseEntity<ProductDTO> restoreProduct(@PathVariable UUID id) {
        Optional<Product> product = productService.restoreProduct(id);
        if (product.isPresent()) {
            ProductDTO productDTO = productMapper.toDTO(product.get());
            return ResponseEntity.ok(productDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/deleted")
    @Operation(summary = "Get deleted products (Admin)", description = "Get all soft-deleted products")
    public ResponseEntity<Page<ProductDTO>> getDeletedProducts(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("deletedAt").descending());
        Page<Product> products = productService.getDeletedProducts(pageable);
        Page<ProductDTO> productDTOs = products.map(productMapper::toDTO);
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/stats/detailed")
    @Operation(summary = "Get detailed product statistics (Admin)", description = "Get comprehensive product statistics")
    public ResponseEntity<AdminProductStats> getDetailedProductStats() {
        long totalProducts = productService.getTotalProductCount();
        long deletedProducts = productService.getDeletedProductCount();
        long availableProducts = productService.getAvailableProductCount();
        long outOfStockProducts = productService.getOutOfStockProductCount();
        
        AdminProductStats stats = new AdminProductStats(totalProducts, deletedProducts, availableProducts, outOfStockProducts);
        return ResponseEntity.ok(stats);
    }
    
    @PostMapping("/bulk-delete")
    @Operation(summary = "Bulk delete products (Admin)", description = "Delete multiple products at once")
    public ResponseEntity<ProductService.BulkOperationResult> bulkDeleteProducts(@RequestBody List<UUID> productIds) {
        ProductService.BulkOperationResult result = productService.bulkDeleteProducts(productIds);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/bulk-restore")
    @Operation(summary = "Bulk restore products (Admin)", description = "Restore multiple deleted products at once")
    public ResponseEntity<ProductService.BulkOperationResult> bulkRestoreProducts(@RequestBody List<UUID> productIds) {
        ProductService.BulkOperationResult result = productService.bulkRestoreProducts(productIds);
        return ResponseEntity.ok(result);
    }
    
    // Inner classes for responses
    public static class AdminProductStats {
        private long totalProducts;
        private long deletedProducts;
        private long availableProducts;
        private long outOfStockProducts;
        
        public AdminProductStats(long totalProducts, long deletedProducts, long availableProducts, long outOfStockProducts) {
            this.totalProducts = totalProducts;
            this.deletedProducts = deletedProducts;
            this.availableProducts = availableProducts;
            this.outOfStockProducts = outOfStockProducts;
        }
        
        // Getters and setters
        public long getTotalProducts() { return totalProducts; }
        public void setTotalProducts(long totalProducts) { this.totalProducts = totalProducts; }
        
        public long getDeletedProducts() { return deletedProducts; }
        public void setDeletedProducts(long deletedProducts) { this.deletedProducts = deletedProducts; }
        
        public long getAvailableProducts() { return availableProducts; }
        public void setAvailableProducts(long availableProducts) { this.availableProducts = availableProducts; }
        
        public long getOutOfStockProducts() { return outOfStockProducts; }
        public void setOutOfStockProducts(long outOfStockProducts) { this.outOfStockProducts = outOfStockProducts; }
    }
    
    public static class BulkOperationResult {
        private int successCount;
        private int failureCount;
        private List<String> errors;
        
        public BulkOperationResult(int successCount, int failureCount, List<String> errors) {
            this.successCount = successCount;
            this.failureCount = failureCount;
            this.errors = errors;
        }
        
        // Getters and setters
        public int getSuccessCount() { return successCount; }
        public void setSuccessCount(int successCount) { this.successCount = successCount; }
        
        public int getFailureCount() { return failureCount; }
        public void setFailureCount(int failureCount) { this.failureCount = failureCount; }
        
        public List<String> getErrors() { return errors; }
        public void setErrors(List<String> errors) { this.errors = errors; }
    }
}
