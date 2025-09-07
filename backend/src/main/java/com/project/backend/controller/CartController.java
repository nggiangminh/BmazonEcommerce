package com.project.backend.controller;

import com.project.backend.dto.request.AddToCartRequest;
import com.project.backend.dto.request.UpdateCartItemRequest;
import com.project.backend.dto.response.CartDTO;
import com.project.backend.entity.Cart;
import com.project.backend.mapper.CartMapper;
import com.project.backend.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart Management", description = "APIs for managing shopping cart")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private CartMapper cartMapper;
    
    @GetMapping
    @Operation(summary = "Get user's cart", description = "Retrieve the current user's shopping cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved cart"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CartDTO> getCart(@RequestParam UUID userId) {
        Cart cart = cartService.getOrCreateCart(userId);
        CartDTO cartDTO = cartMapper.toDTO(cart);
        return ResponseEntity.ok(cartDTO);
    }
    
    @PostMapping("/add")
    @Operation(summary = "Add item to cart", description = "Add a product to the shopping cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Item added to cart successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CartDTO> addToCart(
            @RequestParam UUID userId,
            @Valid @RequestBody AddToCartRequest request) {
        
        cartService.addItemToCart(userId, request.getProductSkuId(), request.getQuantity());
        Cart cart = cartService.getOrCreateCart(userId);
        CartDTO cartDTO = cartMapper.toDTO(cart);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(cartDTO);
    }
    
    @PutMapping("/update")
    @Operation(summary = "Update cart item quantity", description = "Update the quantity of an item in the cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cart item updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Cart item not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CartDTO> updateCartItem(
            @RequestParam UUID userId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        
        cartService.updateCartItemQuantity(userId, request.getCartItemId(), request.getQuantity());
        Cart cart = cartService.getOrCreateCart(userId);
        CartDTO cartDTO = cartMapper.toDTO(cart);
        
        return ResponseEntity.ok(cartDTO);
    }
    
    @DeleteMapping("/remove/{cartItemId}")
    @Operation(summary = "Remove item from cart", description = "Remove a specific item from the cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item removed from cart successfully"),
        @ApiResponse(responseCode = "404", description = "Cart item not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CartDTO> removeFromCart(
            @RequestParam UUID userId,
            @PathVariable UUID cartItemId) {
        
        cartService.removeItemFromCart(userId, cartItemId);
        Cart cart = cartService.getOrCreateCart(userId);
        CartDTO cartDTO = cartMapper.toDTO(cart);
        
        return ResponseEntity.ok(cartDTO);
    }
    
    @DeleteMapping("/remove-product/{productId}")
    @Operation(summary = "Remove product from cart", description = "Remove all items of a specific product from the cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product removed from cart successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CartDTO> removeProductFromCart(
            @RequestParam UUID userId,
            @PathVariable UUID productId) {
        
        cartService.removeProductFromCart(userId, productId);
        Cart cart = cartService.getOrCreateCart(userId);
        CartDTO cartDTO = cartMapper.toDTO(cart);
        
        return ResponseEntity.ok(cartDTO);
    }
    
    @DeleteMapping("/clear")
    @Operation(summary = "Clear cart", description = "Remove all items from the cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cart cleared successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CartDTO> clearCart(@RequestParam UUID userId) {
        cartService.clearCart(userId);
        Cart cart = cartService.getOrCreateCart(userId);
        CartDTO cartDTO = cartMapper.toDTO(cart);
        
        return ResponseEntity.ok(cartDTO);
    }
    
    @GetMapping("/count")
    @Operation(summary = "Get cart item count", description = "Get the total number of items in the cart")
    public ResponseEntity<CartItemCount> getCartItemCount(@RequestParam UUID userId) {
        int count = cartService.getCartItemCount(userId);
        return ResponseEntity.ok(new CartItemCount(count));
    }
    
    @GetMapping("/empty")
    @Operation(summary = "Check if cart is empty", description = "Check if the cart is empty")
    public ResponseEntity<CartEmptyStatus> isCartEmpty(@RequestParam UUID userId) {
        boolean isEmpty = cartService.isCartEmpty(userId);
        return ResponseEntity.ok(new CartEmptyStatus(isEmpty));
    }
    
    @GetMapping("/has-item")
    @Operation(summary = "Check if product is in cart", description = "Check if a specific product SKU is in the cart")
    public ResponseEntity<CartHasItemStatus> hasItemInCart(
            @RequestParam UUID userId,
            @RequestParam UUID productSkuId) {
        
        boolean hasItem = cartService.hasItemInCart(userId, productSkuId);
        return ResponseEntity.ok(new CartHasItemStatus(hasItem));
    }
    
    @PostMapping("/merge")
    @Operation(summary = "Merge carts", description = "Merge two carts (admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CartDTO> mergeCarts(
            @RequestParam UUID primaryUserId,
            @RequestParam UUID secondaryUserId) {
        
        Cart mergedCart = cartService.mergeCarts(primaryUserId, secondaryUserId);
        CartDTO cartDTO = cartMapper.toDTO(mergedCart);
        
        return ResponseEntity.ok(cartDTO);
    }
    
    @PostMapping("/copy")
    @Operation(summary = "Copy cart", description = "Copy cart from one user to another (admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CartDTO> copyCart(
            @RequestParam UUID fromUserId,
            @RequestParam UUID toUserId) {
        
        Cart copiedCart = cartService.copyCart(fromUserId, toUserId);
        CartDTO cartDTO = cartMapper.toDTO(copiedCart);
        
        return ResponseEntity.ok(cartDTO);
    }
    
    @GetMapping("/stats")
    @Operation(summary = "Get cart statistics", description = "Get cart statistics (admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CartStats> getCartStats() {
        long totalCarts = cartService.getTotalCarts();
        long activeCarts = cartService.getActiveCarts();
        
        CartStats stats = new CartStats(totalCarts, activeCarts);
        return ResponseEntity.ok(stats);
    }
    
    // Inner classes for responses
    public static class CartItemCount {
        private int count;
        
        public CartItemCount(int count) {
            this.count = count;
        }
        
        public int getCount() { return count; }
        public void setCount(int count) { this.count = count; }
    }
    
    public static class CartEmptyStatus {
        private boolean empty;
        
        public CartEmptyStatus(boolean empty) {
            this.empty = empty;
        }
        
        public boolean isEmpty() { return empty; }
        public void setEmpty(boolean empty) { this.empty = empty; }
    }
    
    public static class CartHasItemStatus {
        private boolean hasItem;
        
        public CartHasItemStatus(boolean hasItem) {
            this.hasItem = hasItem;
        }
        
        public boolean isHasItem() { return hasItem; }
        public void setHasItem(boolean hasItem) { this.hasItem = hasItem; }
    }
    
    public static class CartStats {
        private long totalCarts;
        private long activeCarts;
        
        public CartStats(long totalCarts, long activeCarts) {
            this.totalCarts = totalCarts;
            this.activeCarts = activeCarts;
        }
        
        public long getTotalCarts() { return totalCarts; }
        public void setTotalCarts(long totalCarts) { this.totalCarts = totalCarts; }
        
        public long getActiveCarts() { return activeCarts; }
        public void setActiveCarts(long activeCarts) { this.activeCarts = activeCarts; }
    }
}
