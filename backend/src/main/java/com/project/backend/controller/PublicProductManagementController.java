package com.project.backend.controller;

import com.project.backend.dto.response.ProductDetailDTO;
import com.project.backend.dto.response.ProductReviewDTO;
import com.project.backend.dto.response.ProductRecommendationDTO;
import com.project.backend.service.ProductManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/products")
@Tag(name = "Public Product Management", description = "Public APIs for product details, reviews, and recommendations without authentication")
public class PublicProductManagementController {
    
    @Autowired
    private ProductManagementService productManagementService;
    
    @GetMapping("/{productId}/details")
    @Operation(summary = "Get product details (Public)", description = "Get detailed product information with reviews and recommendations without authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product details retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductDetailDTO> getProductDetails(
            @PathVariable UUID productId,
            HttpServletRequest request) {
        
        // Track product view for anonymous user
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        productManagementService.trackProductView(productId, ipAddress, userAgent);
        
        ProductDetailDTO productDetails = productManagementService.getProductDetails(productId);
        return ResponseEntity.ok(productDetails);
    }
    
    @GetMapping("/{productId}/reviews")
    @Operation(summary = "Get product reviews (Public)", description = "Get paginated product reviews without authentication")
    public ResponseEntity<Page<ProductReviewDTO>> getProductReviews(
            @PathVariable UUID productId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductReviewDTO> reviews = productManagementService.getProductReviews(productId, pageable);
        return ResponseEntity.ok(reviews);
    }
    
    @GetMapping("/{productId}/reviews/recent")
    @Operation(summary = "Get recent product reviews (Public)", description = "Get recent product reviews without authentication")
    public ResponseEntity<List<ProductReviewDTO>> getRecentProductReviews(
            @PathVariable UUID productId,
            @Parameter(description = "Number of recent reviews") @RequestParam(defaultValue = "5") int limit) {
        
        List<ProductReviewDTO> reviews = productManagementService.getRecentProductReviews(productId, limit);
        return ResponseEntity.ok(reviews);
    }
    
    @GetMapping("/{productId}/reviews/helpful")
    @Operation(summary = "Get most helpful reviews (Public)", description = "Get most helpful product reviews without authentication")
    public ResponseEntity<List<ProductReviewDTO>> getMostHelpfulReviews(
            @PathVariable UUID productId,
            @Parameter(description = "Number of helpful reviews") @RequestParam(defaultValue = "5") int limit) {
        
        List<ProductReviewDTO> reviews = productManagementService.getMostHelpfulReviews(productId, limit);
        return ResponseEntity.ok(reviews);
    }
    
    @GetMapping("/{productId}/reviews/verified")
    @Operation(summary = "Get verified purchase reviews (Public)", description = "Get verified purchase reviews without authentication")
    public ResponseEntity<List<ProductReviewDTO>> getVerifiedPurchaseReviews(
            @PathVariable UUID productId,
            @Parameter(description = "Number of verified reviews") @RequestParam(defaultValue = "5") int limit) {
        
        List<ProductReviewDTO> reviews = productManagementService.getVerifiedPurchaseReviews(productId, limit);
        return ResponseEntity.ok(reviews);
    }
    
    @GetMapping("/{productId}/reviews/statistics")
    @Operation(summary = "Get product review statistics (Public)", description = "Get product review statistics without authentication")
    public ResponseEntity<ProductManagementService.ProductReviewStatistics> getProductReviewStatistics(
            @PathVariable UUID productId) {
        
        ProductManagementService.ProductReviewStatistics statistics = 
            productManagementService.getProductReviewStatistics(productId);
        return ResponseEntity.ok(statistics);
    }
    
    @GetMapping("/{productId}/similar")
    @Operation(summary = "Get similar products (Public)", description = "Get products similar to the specified product without authentication")
    public ResponseEntity<List<ProductRecommendationDTO>> getSimilarProducts(
            @PathVariable UUID productId,
            @Parameter(description = "Number of similar products") @RequestParam(defaultValue = "4") int limit) {
        
        List<ProductRecommendationDTO> similarProducts = 
            productManagementService.getSimilarProducts(productId, limit);
        return ResponseEntity.ok(similarProducts);
    }
    
    @GetMapping("/recommendations/trending")
    @Operation(summary = "Get trending products (Public)", description = "Get trending products without authentication")
    public ResponseEntity<List<ProductRecommendationDTO>> getTrendingProducts(
            @Parameter(description = "Number of trending products") @RequestParam(defaultValue = "8") int limit) {
        
        List<ProductRecommendationDTO> trendingProducts = 
            productManagementService.getTrendingProducts(limit);
        return ResponseEntity.ok(trendingProducts);
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
