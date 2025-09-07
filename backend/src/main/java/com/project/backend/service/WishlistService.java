package com.project.backend.service;

import com.project.backend.entity.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface WishlistService {
    
    // Basic wishlist operations
    Wishlist addToWishlist(UUID userId, UUID productId);
    void removeFromWishlist(UUID userId, UUID productId);
    void clearWishlist(UUID userId);
    List<Wishlist> getUserWishlist(UUID userId);
    Page<Wishlist> getUserWishlist(UUID userId, Pageable pageable);
    
    // Check operations
    boolean isInWishlist(UUID userId, UUID productId);
    int getWishlistCount(UUID userId);
    boolean isWishlistEmpty(UUID userId);
    
    // Product operations
    List<Wishlist> getWishlistByProduct(UUID productId);
    long getProductWishlistCount(UUID productId);
    List<Wishlist> getMostWishlistedProducts(int limit);
    
    // Category operations
    List<Wishlist> getWishlistByCategory(UUID userId, UUID categoryId);
    
    // Recent operations
    List<Wishlist> getRecentWishlist(UUID userId, int limit);
    
    // Statistics
    long getTotalWishlistCount();
    long getActiveWishlistCount();
    
    // Bulk operations
    void bulkAddToWishlist(UUID userId, List<UUID> productIds);
    void bulkRemoveFromWishlist(UUID userId, List<UUID> productIds);
    
    // Admin operations
    List<Wishlist> getAllWishlists();
    Page<Wishlist> getAllWishlists(Pageable pageable);
    void deleteWishlist(UUID wishlistId);
    void restoreWishlist(UUID wishlistId);
    
    // Move operations
    void moveToCart(UUID userId, UUID productId);
    void moveAllToCart(UUID userId);
}
