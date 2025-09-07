package com.project.backend.dto.response;

import com.project.backend.entity.Cart;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CartDTO {
    
    private UUID id;
    private UUID userId;
    private List<CartItemDTO> cartItems;
    private BigDecimal total;
    private int totalItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public CartDTO() {}
    
    public CartDTO(Cart cart) {
        this.id = cart.getId();
        this.userId = cart.getUser().getId();
        this.cartItems = cart.getCartItems() != null ? 
            cart.getCartItems().stream()
                .map(CartItemDTO::new)
                .collect(Collectors.toList()) : null;
        this.total = cart.getTotal();
        this.totalItems = cart.getTotalItems();
        this.createdAt = cart.getCreatedAt();
        this.updatedAt = cart.getUpdatedAt();
    }
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public UUID getUserId() {
        return userId;
    }
    
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    
    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }
    
    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public int getTotalItems() {
        return totalItems;
    }
    
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Nested DTO for CartItem
    public static class CartItemDTO {
        private UUID id;
        private UUID productId;
        private String productName;
        private String productSku;
        private String size;
        private String color;
        private BigDecimal price;
        private int quantity;
        private BigDecimal subtotal;
        private int availableQuantity;
        private boolean available;
        private String productImage;
        
        public CartItemDTO() {}
        
        public CartItemDTO(com.project.backend.entity.CartItem cartItem) {
            this.id = cartItem.getId();
            this.productId = cartItem.getProduct().getId();
            this.productName = cartItem.getProduct().getName();
            this.productSku = cartItem.getProductSku().getSku();
            this.size = cartItem.getProductSku().getSizeAttribute() != null ? 
                cartItem.getProductSku().getSizeAttribute().getValue() : null;
            this.color = cartItem.getProductSku().getColorAttribute() != null ? 
                cartItem.getProductSku().getColorAttribute().getValue() : null;
            this.price = cartItem.getProductSku().getPrice();
            this.quantity = cartItem.getQuantity();
            this.subtotal = cartItem.getSubtotal();
            this.availableQuantity = cartItem.getAvailableQuantity();
            this.available = cartItem.isAvailable();
            this.productImage = cartItem.getProduct().getCover();
        }
        
        // Getters and setters
        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        
        public UUID getProductId() { return productId; }
        public void setProductId(UUID productId) { this.productId = productId; }
        
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        
        public String getProductSku() { return productSku; }
        public void setProductSku(String productSku) { this.productSku = productSku; }
        
        public String getSize() { return size; }
        public void setSize(String size) { this.size = size; }
        
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
        
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        
        public BigDecimal getSubtotal() { return subtotal; }
        public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
        
        public int getAvailableQuantity() { return availableQuantity; }
        public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }
        
        public boolean isAvailable() { return available; }
        public void setAvailable(boolean available) { this.available = available; }
        
        public String getProductImage() { return productImage; }
        public void setProductImage(String productImage) { this.productImage = productImage; }
    }
}
