package com.project.backend.controller;

import com.project.backend.dto.request.CreateProductReviewRequest;
import com.project.backend.dto.request.UpdateProductReviewRequest;
import com.project.backend.dto.response.ProductDetailDTO;
import com.project.backend.dto.response.ProductReviewDTO;
import com.project.backend.dto.response.ProductRecommendationDTO;
import com.project.backend.service.ProductManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "APIs for product details, reviews, and recommendations")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class ProductManagementController {
    
    @Autowired
    private ProductManagementService productManagementService;
    
    @GetMapping("/{productId}/details")
    @Operation(summary = "Get product details", description = "Get detailed product information with reviews and recommendations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product details retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductDetailDTO> getProductDetails(
            @PathVariable UUID productId,
            @RequestParam UUID userId,
            HttpServletRequest request) {
        
        // Track product view
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        productManagementService.trackProductView(productId, userId, ipAddress, userAgent);
        
        ProductDetailDTO productDetails = productManagementService.getProductDetails(productId, userId);
        return ResponseEntity.ok(productDetails);
    }
    
    @PostMapping("/{productId}/reviews")
    @Operation(summary = "Create product review", description = "Create a new product review")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Review created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid review data"),
        @ApiResponse(responseCode = "409", description = "User has already reviewed this product"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductReviewDTO> createProductReview(
            @PathVariable UUID productId,
            @RequestParam UUID userId,
            @Valid @RequestBody CreateProductReviewRequest request) {
        
        ProductReviewDTO review = productManagementService.createProductReview(userId, productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }
    
    @PutMapping("/reviews/{reviewId}")
    @Operation(summary = "Update product review", description = "Update an existing product review")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Review updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid review data"),
        @ApiResponse(responseCode = "404", description = "Review not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to update this review"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductReviewDTO> updateProductReview(
            @PathVariable UUID reviewId,
            @RequestParam UUID userId,
            @Valid @RequestBody UpdateProductReviewRequest request) {
        
        ProductReviewDTO review = productManagementService.updateProductReview(userId, reviewId, request);
        return ResponseEntity.ok(review);
    }
    
    @DeleteMapping("/reviews/{reviewId}")
    @Operation(summary = "Delete product review", description = "Delete a product review")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Review deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Review not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to delete this review"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteProductReview(
            @PathVariable UUID reviewId,
            @RequestParam UUID userId) {
        
        productManagementService.deleteProductReview(userId, reviewId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{productId}/reviews")
    @Operation(summary = "Get product reviews", description = "Get paginated product reviews")
    public ResponseEntity<Page<ProductReviewDTO>> getProductReviews(
            @PathVariable UUID productId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductReviewDTO> reviews = productManagementService.getProductReviews(productId, pageable);
        return ResponseEntity.ok(reviews);
    }
    
    @GetMapping("/{productId}/reviews/recent")
    @Operation(summary = "Get recent product reviews", description = "Get recent product reviews")
    public ResponseEntity<List<ProductReviewDTO>> getRecentProductReviews(
            @PathVariable UUID productId,
            @Parameter(description = "Number of recent reviews") @RequestParam(defaultValue = "5") int limit) {
        
        List<ProductReviewDTO> reviews = productManagementService.getRecentProductReviews(productId, limit);
        return ResponseEntity.ok(reviews);
    }
    
    @GetMapping("/{productId}/reviews/helpful")
    @Operation(summary = "Get most helpful reviews", description = "Get most helpful product reviews")
    public ResponseEntity<List<ProductReviewDTO>> getMostHelpfulReviews(
            @PathVariable UUID productId,
            @Parameter(description = "Number of helpful reviews") @RequestParam(defaultValue = "5") int limit) {
        
        List<ProductReviewDTO> reviews = productManagementService.getMostHelpfulReviews(productId, limit);
        return ResponseEntity.ok(reviews);
    }
    
    @GetMapping("/{productId}/reviews/verified")
    @Operation(summary = "Get verified purchase reviews", description = "Get verified purchase reviews")
    public ResponseEntity<List<ProductReviewDTO>> getVerifiedPurchaseReviews(
            @PathVariable UUID productId,
            @Parameter(description = "Number of verified reviews") @RequestParam(defaultValue = "5") int limit) {
        
        List<ProductReviewDTO> reviews = productManagementService.getVerifiedPurchaseReviews(productId, limit);
        return ResponseEntity.ok(reviews);
    }
    
    @GetMapping("/{productId}/reviews/statistics")
    @Operation(summary = "Get product review statistics", description = "Get product review statistics")
    public ResponseEntity<ProductManagementService.ProductReviewStatistics> getProductReviewStatistics(
            @PathVariable UUID productId) {
        
        ProductManagementService.ProductReviewStatistics statistics = 
            productManagementService.getProductReviewStatistics(productId);
        return ResponseEntity.ok(statistics);
    }
    
    @GetMapping("/{productId}/similar")
    @Operation(summary = "Get similar products", description = "Get products similar to the specified product")
    public ResponseEntity<List<ProductRecommendationDTO>> getSimilarProducts(
            @PathVariable UUID productId,
            @Parameter(description = "Number of similar products") @RequestParam(defaultValue = "4") int limit) {
        
        List<ProductRecommendationDTO> similarProducts = 
            productManagementService.getSimilarProducts(productId, limit);
        return ResponseEntity.ok(similarProducts);
    }
    
    @GetMapping("/recommendations")
    @Operation(summary = "Get product recommendations", description = "Get personalized product recommendations")
    public ResponseEntity<List<ProductRecommendationDTO>> getProductRecommendations(
            @RequestParam UUID userId,
            @Parameter(description = "Number of recommendations") @RequestParam(defaultValue = "6") int limit) {
        
        List<ProductRecommendationDTO> recommendations = 
            productManagementService.getProductRecommendations(userId, limit);
        return ResponseEntity.ok(recommendations);
    }
    
    @GetMapping("/recommendations/personalized")
    @Operation(summary = "Get personalized recommendations", description = "Get personalized product recommendations")
    public ResponseEntity<List<ProductRecommendationDTO>> getPersonalizedRecommendations(
            @RequestParam UUID userId,
            @Parameter(description = "Number of recommendations") @RequestParam(defaultValue = "6") int limit) {
        
        List<ProductRecommendationDTO> recommendations = 
            productManagementService.getPersonalizedRecommendations(userId, limit);
        return ResponseEntity.ok(recommendations);
    }
    
    @GetMapping("/trending")
    @Operation(summary = "Get trending products", description = "Get trending products")
    public ResponseEntity<List<ProductRecommendationDTO>> getTrendingProducts(
            @Parameter(description = "Number of trending products") @RequestParam(defaultValue = "8") int limit) {
        
        List<ProductRecommendationDTO> trendingProducts = 
            productManagementService.getTrendingProducts(limit);
        return ResponseEntity.ok(trendingProducts);
    }
    
    @PostMapping("/recommendations/{recommendationId}/view")
    @Operation(summary = "Mark recommendation as viewed", description = "Mark a recommendation as viewed")
    public ResponseEntity<Void> markRecommendationAsViewed(
            @PathVariable UUID recommendationId,
            @RequestParam UUID userId) {
        
        productManagementService.markRecommendationAsViewed(userId, recommendationId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/recommendations/{recommendationId}/click")
    @Operation(summary = "Mark recommendation as clicked", description = "Mark a recommendation as clicked")
    public ResponseEntity<Void> markRecommendationAsClicked(
            @PathVariable UUID recommendationId,
            @RequestParam UUID userId) {
        
        productManagementService.markRecommendationAsClicked(userId, recommendationId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{productId}/analytics")
    @Operation(summary = "Get product analytics", description = "Get product analytics (admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductManagementService.ProductAnalytics> getProductAnalytics(
            @PathVariable UUID productId) {
        
        ProductManagementService.ProductAnalytics analytics = 
            productManagementService.getProductAnalytics(productId);
        return ResponseEntity.ok(analytics);
    }
    
    // Helper method to get client IP address
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
}
