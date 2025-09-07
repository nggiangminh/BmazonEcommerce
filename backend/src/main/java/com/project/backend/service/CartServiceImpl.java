package com.project.backend.service;

import com.project.backend.entity.Cart;
import com.project.backend.entity.CartItem;
import com.project.backend.entity.ProductSku;
import com.project.backend.entity.User;
import com.project.backend.exception.ResourceNotFoundException;
import com.project.backend.repository.CartItemRepository;
import com.project.backend.repository.CartRepository;
import com.project.backend.repository.ProductSkuRepository;
import com.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductSkuRepository productSkuRepository;
    
    @Override
    @Transactional(readOnly = true)
    public Cart getOrCreateCart(UUID userId) {
        Optional<Cart> existingCart = cartRepository.findByUserIdWithItems(userId);
        if (existingCart.isPresent()) {
            return existingCart.get();
        }
        
        // Create new cart
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotal(java.math.BigDecimal.ZERO);
        
        return cartRepository.save(cart);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Cart> getCartByUserId(UUID userId) {
        return cartRepository.findByUserIdWithItems(userId);
    }
    
    @Override
    public Cart saveCart(Cart cart) {
        cart.calculateTotal();
        return cartRepository.save(cart);
    }
    
    @Override
    public void deleteCart(UUID cartId) {
        cartRepository.deleteById(cartId);
    }
    
    @Override
    public void clearCart(UUID userId) {
        Cart cart = getOrCreateCart(userId);
        cart.clear();
        saveCart(cart);
    }
    
    @Override
    public CartItem addItemToCart(UUID userId, UUID productSkuId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        
        Cart cart = getOrCreateCart(userId);
        ProductSku productSku = productSkuRepository.findByIdActive(productSkuId)
                .orElseThrow(() -> new ResourceNotFoundException("Product SKU not found with id: " + productSkuId));
        
        // Check if item already exists in cart
        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductSkuId(cart.getId(), productSkuId);
        
        if (existingItem.isPresent()) {
            // Update existing item quantity
            CartItem cartItem = existingItem.get();
            int newQuantity = cartItem.getQuantity() + quantity;
            
            // Check stock availability
            if (newQuantity > productSku.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock. Available: " + productSku.getQuantity());
            }
            
            cartItem.setQuantity(newQuantity);
            cartItem = cartItemRepository.save(cartItem);
            saveCart(cart);
            return cartItem;
        } else {
            // Create new cart item
            if (quantity > productSku.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock. Available: " + productSku.getQuantity());
            }
            
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(productSku.getProduct());
            cartItem.setProductSku(productSku);
            cartItem.setQuantity(quantity);
            
            cartItem = cartItemRepository.save(cartItem);
            saveCart(cart);
            return cartItem;
        }
    }
    
    @Override
    public CartItem updateCartItemQuantity(UUID userId, UUID cartItemId, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        
        Cart cart = getOrCreateCart(userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));
        
        // Verify cart item belongs to user's cart
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("Cart item does not belong to user's cart");
        }
        
        if (quantity == 0) {
            // Remove item if quantity is 0
            cartItemRepository.delete(cartItem);
            saveCart(cart);
            return null;
        }
        
        // Check stock availability
        if (quantity > cartItem.getProductSku().getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock. Available: " + cartItem.getProductSku().getQuantity());
        }
        
        cartItem.setQuantity(quantity);
        cartItem = cartItemRepository.save(cartItem);
        saveCart(cart);
        return cartItem;
    }
    
    @Override
    public void removeItemFromCart(UUID userId, UUID cartItemId) {
        Cart cart = getOrCreateCart(userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));
        
        // Verify cart item belongs to user's cart
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("Cart item does not belong to user's cart");
        }
        
        cartItemRepository.delete(cartItem);
        saveCart(cart);
    }
    
    @Override
    public void removeProductFromCart(UUID userId, UUID productId) {
        Cart cart = getOrCreateCart(userId);
        List<CartItem> cartItems = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);
        
        cartItemRepository.deleteAll(cartItems);
        saveCart(cart);
    }
    
    @Override
    public Cart mergeCarts(UUID primaryUserId, UUID secondaryUserId) {
        Cart primaryCart = getOrCreateCart(primaryUserId);
        Optional<Cart> secondaryCartOpt = getCartByUserId(secondaryUserId);
        
        if (secondaryCartOpt.isEmpty()) {
            return primaryCart;
        }
        
        Cart secondaryCart = secondaryCartOpt.get();
        
        // Merge items from secondary cart to primary cart
        for (CartItem secondaryItem : secondaryCart.getCartItems()) {
            try {
                addItemToCart(primaryUserId, secondaryItem.getProductSku().getId(), secondaryItem.getQuantity());
            } catch (Exception e) {
                // Log error but continue merging other items
                System.err.println("Failed to merge cart item: " + e.getMessage());
            }
        }
        
        // Clear secondary cart
        clearCart(secondaryUserId);
        
        return getOrCreateCart(primaryUserId);
    }
    
    @Override
    public Cart copyCart(UUID fromUserId, UUID toUserId) {
        Cart fromCart = getOrCreateCart(fromUserId);
        Cart toCart = getOrCreateCart(toUserId);
        
        // Clear destination cart first
        toCart.clear();
        saveCart(toCart);
        
        // Copy items
        for (CartItem fromItem : fromCart.getCartItems()) {
            try {
                addItemToCart(toUserId, fromItem.getProductSku().getId(), fromItem.getQuantity());
            } catch (Exception e) {
                // Log error but continue copying other items
                System.err.println("Failed to copy cart item: " + e.getMessage());
            }
        }
        
        return getOrCreateCart(toUserId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isCartEmpty(UUID userId) {
        Cart cart = getOrCreateCart(userId);
        return cart.isEmpty();
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean hasItemInCart(UUID userId, UUID productSkuId) {
        Cart cart = getOrCreateCart(userId);
        return cartItemRepository.findByCartIdAndProductSkuId(cart.getId(), productSkuId).isPresent();
    }
    
    @Override
    @Transactional(readOnly = true)
    public int getCartItemCount(UUID userId) {
        Cart cart = getOrCreateCart(userId);
        return cart.getTotalItems();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getTotalCarts() {
        return cartRepository.countAllCarts();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getActiveCarts() {
        return cartRepository.countActiveCarts();
    }
    
    @Override
    public void bulkAddItems(UUID userId, List<CartItemRequest> items) {
        for (CartItemRequest item : items) {
            try {
                addItemToCart(userId, item.getProductSkuId(), item.getQuantity());
            } catch (Exception e) {
                // Log error but continue with other items
                System.err.println("Failed to add cart item: " + e.getMessage());
            }
        }
    }
    
    @Override
    public void bulkUpdateItems(UUID userId, List<CartItemUpdateRequest> items) {
        for (CartItemUpdateRequest item : items) {
            try {
                updateCartItemQuantity(userId, item.getCartItemId(), item.getQuantity());
            } catch (Exception e) {
                // Log error but continue with other items
                System.err.println("Failed to update cart item: " + e.getMessage());
            }
        }
    }
    
    @Override
    public void bulkRemoveItems(UUID userId, List<UUID> cartItemIds) {
        for (UUID cartItemId : cartItemIds) {
            try {
                removeItemFromCart(userId, cartItemId);
            } catch (Exception e) {
                // Log error but continue with other items
                System.err.println("Failed to remove cart item: " + e.getMessage());
            }
        }
    }
}
