package com.project.backend.service;

import com.project.backend.entity.Cart;
import com.project.backend.entity.CartItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartService {
    
    // Basic cart operations
    Cart getOrCreateCart(UUID userId);
    Optional<Cart> getCartByUserId(UUID userId);
    Cart saveCart(Cart cart);
    void deleteCart(UUID cartId);
    void clearCart(UUID userId);
    
    // Cart item operations
    CartItem addItemToCart(UUID userId, UUID productSkuId, int quantity);
    CartItem updateCartItemQuantity(UUID userId, UUID cartItemId, int quantity);
    void removeItemFromCart(UUID userId, UUID cartItemId);
    void removeProductFromCart(UUID userId, UUID productId);
    
    // Cart management
    Cart mergeCarts(UUID primaryUserId, UUID secondaryUserId);
    Cart copyCart(UUID fromUserId, UUID toUserId);
    
    // Validation and checks
    boolean isCartEmpty(UUID userId);
    boolean hasItemInCart(UUID userId, UUID productSkuId);
    int getCartItemCount(UUID userId);
    
    // Statistics
    long getTotalCarts();
    long getActiveCarts();
    
    // Bulk operations
    void bulkAddItems(UUID userId, List<CartItemRequest> items);
    void bulkUpdateItems(UUID userId, List<CartItemUpdateRequest> items);
    void bulkRemoveItems(UUID userId, List<UUID> cartItemIds);
    
    // Inner classes for requests
    class CartItemRequest {
        private UUID productSkuId;
        private int quantity;
        
        public CartItemRequest() {}
        
        public CartItemRequest(UUID productSkuId, int quantity) {
            this.productSkuId = productSkuId;
            this.quantity = quantity;
        }
        
        public UUID getProductSkuId() { return productSkuId; }
        public void setProductSkuId(UUID productSkuId) { this.productSkuId = productSkuId; }
        
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }
    
    class CartItemUpdateRequest {
        private UUID cartItemId;
        private int quantity;
        
        public CartItemUpdateRequest() {}
        
        public CartItemUpdateRequest(UUID cartItemId, int quantity) {
            this.cartItemId = cartItemId;
            this.quantity = quantity;
        }
        
        public UUID getCartItemId() { return cartItemId; }
        public void setCartItemId(UUID cartItemId) { this.cartItemId = cartItemId; }
        
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }
}
