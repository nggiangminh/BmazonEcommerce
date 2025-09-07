package com.project.backend.service;

import com.project.backend.entity.Product;
import com.project.backend.entity.User;
import com.project.backend.entity.Wishlist;
import com.project.backend.exception.DuplicateResourceException;
import com.project.backend.exception.ResourceNotFoundException;
import com.project.backend.repository.ProductRepository;
import com.project.backend.repository.UserRepository;
import com.project.backend.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class WishlistServiceImpl implements WishlistService {
    
    @Autowired
    private WishlistRepository wishlistRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CartService cartService;
    
    @Override
    public Wishlist addToWishlist(UUID userId, UUID productId) {
        // Check if already in wishlist
        if (isInWishlist(userId, productId)) {
            throw new DuplicateResourceException("Product is already in wishlist");
        }
        
        // Check if previously deleted and restore
        Optional<Wishlist> existingWishlist = wishlistRepository.findByUserIdAndProductId(userId, productId);
        if (existingWishlist.isPresent()) {
            Wishlist wishlist = existingWishlist.get();
            if (wishlist.isDeleted()) {
                wishlist.restore();
                return wishlistRepository.save(wishlist);
            }
        }
        
        // Validate user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        // Validate product exists and is active
        Product product = productRepository.findByIdActive(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        
        // Create new wishlist item
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);
        
        return wishlistRepository.save(wishlist);
    }
    
    @Override
    public void removeFromWishlist(UUID userId, UUID productId) {
        Wishlist wishlist = wishlistRepository.findByUserIdAndProductIdActive(userId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in wishlist"));
        
        wishlist.softDelete();
        wishlistRepository.save(wishlist);
    }
    
    @Override
    public void clearWishlist(UUID userId) {
        wishlistRepository.softDeleteByUserId(userId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Wishlist> getUserWishlist(UUID userId) {
        return wishlistRepository.findByUserIdWithProductDetails(userId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Wishlist> getUserWishlist(UUID userId, Pageable pageable) {
        return wishlistRepository.findByUserIdActive(userId, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isInWishlist(UUID userId, UUID productId) {
        return wishlistRepository.existsByUserIdAndProductIdActive(userId, productId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public int getWishlistCount(UUID userId) {
        return (int) wishlistRepository.countByUserIdActive(userId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isWishlistEmpty(UUID userId) {
        return getWishlistCount(userId) == 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Wishlist> getWishlistByProduct(UUID productId) {
        return wishlistRepository.findByProductIdActive(productId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getProductWishlistCount(UUID productId) {
        return wishlistRepository.countByProductIdActive(productId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Wishlist> getMostWishlistedProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Object[]> results = wishlistRepository.findMostWishlistedProducts(pageable);
        
        return results.stream()
                .map(result -> (Wishlist) result[0])
                .toList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Wishlist> getWishlistByCategory(UUID userId, UUID categoryId) {
        return wishlistRepository.findByUserIdAndCategoryIdActive(userId, categoryId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Wishlist> getRecentWishlist(UUID userId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return wishlistRepository.findRecentByUserId(userId, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getTotalWishlistCount() {
        return wishlistRepository.countAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getActiveWishlistCount() {
        return wishlistRepository.countActive();
    }
    
    @Override
    public void bulkAddToWishlist(UUID userId, List<UUID> productIds) {
        for (UUID productId : productIds) {
            try {
                addToWishlist(userId, productId);
            } catch (Exception e) {
                // Log error but continue with other products
                System.err.println("Failed to add product to wishlist: " + e.getMessage());
            }
        }
    }
    
    @Override
    public void bulkRemoveFromWishlist(UUID userId, List<UUID> productIds) {
        for (UUID productId : productIds) {
            try {
                removeFromWishlist(userId, productId);
            } catch (Exception e) {
                // Log error but continue with other products
                System.err.println("Failed to remove product from wishlist: " + e.getMessage());
            }
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Wishlist> getAllWishlists(Pageable pageable) {
        return wishlistRepository.findAll(pageable);
    }
    
    @Override
    public void deleteWishlist(UUID wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found with id: " + wishlistId));
        
        wishlistRepository.delete(wishlist);
    }
    
    @Override
    public void restoreWishlist(UUID wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found with id: " + wishlistId));
        
        wishlist.restore();
        wishlistRepository.save(wishlist);
    }
    
    @Override
    public void moveToCart(UUID userId, UUID productId) {
        // Check if product is in wishlist
        Wishlist wishlist = wishlistRepository.findByUserIdAndProductIdActive(userId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in wishlist"));
        
        // Get the first available SKU for the product
        List<com.project.backend.entity.ProductSku> productSkus = wishlist.getProduct().getProductSkus();
        if (productSkus.isEmpty()) {
            throw new ResourceNotFoundException("No SKU available for this product");
        }
        
        // Find first available SKU
        com.project.backend.entity.ProductSku availableSku = productSkus.stream()
                .filter(sku -> sku.getQuantity() > 0)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No available SKU for this product"));
        
        // Add to cart
        cartService.addItemToCart(userId, availableSku.getId(), 1);
        
        // Remove from wishlist
        removeFromWishlist(userId, productId);
    }
    
    @Override
    public void moveAllToCart(UUID userId) {
        List<Wishlist> wishlistItems = getUserWishlist(userId);
        
        for (Wishlist wishlist : wishlistItems) {
            try {
                moveToCart(userId, wishlist.getProduct().getId());
            } catch (Exception e) {
                // Log error but continue with other items
                System.err.println("Failed to move product to cart: " + e.getMessage());
            }
        }
    }
}
