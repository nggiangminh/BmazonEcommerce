package com.project.backend.controller;

import com.project.backend.dto.request.AddToWishlistRequest;
import com.project.backend.dto.response.WishlistDTO;
import com.project.backend.dto.response.WishlistListResponse;
import com.project.backend.entity.Wishlist;
import com.project.backend.mapper.WishlistMapper;
import com.project.backend.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/wishlist")
@Tag(name = "Wishlist Management", description = "APIs for managing user wishlist")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class WishlistController {
    
    @Autowired
    private WishlistService wishlistService;
    
    @Autowired
    private WishlistMapper wishlistMapper;
    
    @GetMapping
    @Operation(summary = "Get user's wishlist", description = "Retrieve the current user's wishlist")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved wishlist"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<WishlistDTO>> getWishlist(@RequestParam UUID userId) {
        List<Wishlist> wishlist = wishlistService.getUserWishlist(userId);
        List<WishlistDTO> wishlistDTOs = wishlistMapper.toDTOList(wishlist);
        return ResponseEntity.ok(wishlistDTOs);
    }
    
    @GetMapping("/paginated")
    @Operation(summary = "Get user's wishlist with pagination", description = "Retrieve the current user's wishlist with pagination")
    public ResponseEntity<WishlistListResponse> getWishlistPaginated(
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Wishlist> wishlistPage = wishlistService.getUserWishlist(userId, pageable);
        Page<WishlistDTO> wishlistDTOPage = wishlistPage.map(wishlistMapper::toDTO);
        WishlistListResponse response = new WishlistListResponse(wishlistDTOPage);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/add")
    @Operation(summary = "Add product to wishlist", description = "Add a product to the user's wishlist")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product added to wishlist successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data or product already in wishlist"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WishlistDTO> addToWishlist(
            @RequestParam UUID userId,
            @Valid @RequestBody AddToWishlistRequest request) {
        
        Wishlist wishlist = wishlistService.addToWishlist(userId, request.getProductId());
        WishlistDTO wishlistDTO = wishlistMapper.toDTO(wishlist);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(wishlistDTO);
    }
    
    @DeleteMapping("/remove/{productId}")
    @Operation(summary = "Remove product from wishlist", description = "Remove a product from the user's wishlist")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product removed from wishlist successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found in wishlist"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WishlistResponse> removeFromWishlist(
            @RequestParam UUID userId,
            @PathVariable UUID productId) {
        
        wishlistService.removeFromWishlist(userId, productId);
        return ResponseEntity.ok(new WishlistResponse("Product removed from wishlist successfully"));
    }
    
    @DeleteMapping("/clear")
    @Operation(summary = "Clear wishlist", description = "Remove all products from the user's wishlist")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Wishlist cleared successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WishlistResponse> clearWishlist(@RequestParam UUID userId) {
        wishlistService.clearWishlist(userId);
        return ResponseEntity.ok(new WishlistResponse("Wishlist cleared successfully"));
    }
    
    @GetMapping("/check/{productId}")
    @Operation(summary = "Check if product is in wishlist", description = "Check if a specific product is in the user's wishlist")
    public ResponseEntity<WishlistStatus> isInWishlist(
            @RequestParam UUID userId,
            @PathVariable UUID productId) {
        
        boolean inWishlist = wishlistService.isInWishlist(userId, productId);
        return ResponseEntity.ok(new WishlistStatus(inWishlist));
    }
    
    @GetMapping("/count")
    @Operation(summary = "Get wishlist count", description = "Get the total number of products in the user's wishlist")
    public ResponseEntity<WishlistCount> getWishlistCount(@RequestParam UUID userId) {
        int count = wishlistService.getWishlistCount(userId);
        return ResponseEntity.ok(new WishlistCount(count));
    }
    
    @GetMapping("/empty")
    @Operation(summary = "Check if wishlist is empty", description = "Check if the user's wishlist is empty")
    public ResponseEntity<WishlistEmptyStatus> isWishlistEmpty(@RequestParam UUID userId) {
        boolean isEmpty = wishlistService.isWishlistEmpty(userId);
        return ResponseEntity.ok(new WishlistEmptyStatus(isEmpty));
    }
    
    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get wishlist by category", description = "Get wishlist items filtered by category")
    public ResponseEntity<List<WishlistDTO>> getWishlistByCategory(
            @RequestParam UUID userId,
            @PathVariable UUID categoryId) {
        
        List<Wishlist> wishlist = wishlistService.getWishlistByCategory(userId, categoryId);
        List<WishlistDTO> wishlistDTOs = wishlistMapper.toDTOList(wishlist);
        return ResponseEntity.ok(wishlistDTOs);
    }
    
    @GetMapping("/recent")
    @Operation(summary = "Get recent wishlist items", description = "Get recently added wishlist items")
    public ResponseEntity<List<WishlistDTO>> getRecentWishlist(
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "10") int limit) {
        
        List<Wishlist> wishlist = wishlistService.getRecentWishlist(userId, limit);
        List<WishlistDTO> wishlistDTOs = wishlistMapper.toDTOList(wishlist);
        return ResponseEntity.ok(wishlistDTOs);
    }
    
    @PostMapping("/move-to-cart/{productId}")
    @Operation(summary = "Move product to cart", description = "Move a product from wishlist to cart")
    public ResponseEntity<WishlistResponse> moveToCart(
            @RequestParam UUID userId,
            @PathVariable UUID productId) {
        
        wishlistService.moveToCart(userId, productId);
        return ResponseEntity.ok(new WishlistResponse("Product moved to cart successfully"));
    }
    
    @PostMapping("/move-all-to-cart")
    @Operation(summary = "Move all products to cart", description = "Move all products from wishlist to cart")
    public ResponseEntity<WishlistResponse> moveAllToCart(@RequestParam UUID userId) {
        wishlistService.moveAllToCart(userId);
        return ResponseEntity.ok(new WishlistResponse("All products moved to cart successfully"));
    }
    
    @PostMapping("/bulk-add")
    @Operation(summary = "Bulk add products to wishlist", description = "Add multiple products to wishlist at once")
    public ResponseEntity<WishlistResponse> bulkAddToWishlist(
            @RequestParam UUID userId,
            @RequestBody List<UUID> productIds) {
        
        wishlistService.bulkAddToWishlist(userId, productIds);
        return ResponseEntity.ok(new WishlistResponse("Products added to wishlist successfully"));
    }
    
    @PostMapping("/bulk-remove")
    @Operation(summary = "Bulk remove products from wishlist", description = "Remove multiple products from wishlist at once")
    public ResponseEntity<WishlistResponse> bulkRemoveFromWishlist(
            @RequestParam UUID userId,
            @RequestBody List<UUID> productIds) {
        
        wishlistService.bulkRemoveFromWishlist(userId, productIds);
        return ResponseEntity.ok(new WishlistResponse("Products removed from wishlist successfully"));
    }
    
    @GetMapping("/stats")
    @Operation(summary = "Get wishlist statistics", description = "Get wishlist statistics (admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WishlistStats> getWishlistStats() {
        long totalWishlists = wishlistService.getTotalWishlistCount();
        long activeWishlists = wishlistService.getActiveWishlistCount();
        
        WishlistStats stats = new WishlistStats(totalWishlists, activeWishlists);
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/most-wishlisted")
    @Operation(summary = "Get most wishlisted products", description = "Get most wishlisted products")
    public ResponseEntity<List<WishlistDTO>> getMostWishlistedProducts(
            @RequestParam(defaultValue = "10") int limit) {
        
        List<Wishlist> wishlist = wishlistService.getMostWishlistedProducts(limit);
        List<WishlistDTO> wishlistDTOs = wishlistMapper.toDTOList(wishlist);
        return ResponseEntity.ok(wishlistDTOs);
    }
    
    // Inner classes for responses
    public static class WishlistResponse {
        private String message;
        
        public WishlistResponse(String message) {
            this.message = message;
        }
        
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
    
    public static class WishlistStatus {
        private boolean inWishlist;
        
        public WishlistStatus(boolean inWishlist) {
            this.inWishlist = inWishlist;
        }
        
        public boolean isInWishlist() { return inWishlist; }
        public void setInWishlist(boolean inWishlist) { this.inWishlist = inWishlist; }
    }
    
    public static class WishlistCount {
        private int count;
        
        public WishlistCount(int count) {
            this.count = count;
        }
        
        public int getCount() { return count; }
        public void setCount(int count) { this.count = count; }
    }
    
    public static class WishlistEmptyStatus {
        private boolean empty;
        
        public WishlistEmptyStatus(boolean empty) {
            this.empty = empty;
        }
        
        public boolean isEmpty() { return empty; }
        public void setEmpty(boolean empty) { this.empty = empty; }
    }
    
    public static class WishlistStats {
        private long totalWishlists;
        private long activeWishlists;
        
        public WishlistStats(long totalWishlists, long activeWishlists) {
            this.totalWishlists = totalWishlists;
            this.activeWishlists = activeWishlists;
        }
        
        public long getTotalWishlists() { return totalWishlists; }
        public void setTotalWishlists(long totalWishlists) { this.totalWishlists = totalWishlists; }
        
        public long getActiveWishlists() { return activeWishlists; }
        public void setActiveWishlists(long activeWishlists) { this.activeWishlists = activeWishlists; }
    }
}
