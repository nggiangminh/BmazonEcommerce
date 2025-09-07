package com.project.backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UpdateCartItemRequest {
    
    @NotNull(message = "Cart item ID is required")
    private UUID cartItemId;
    
    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;
    
    public UpdateCartItemRequest() {}
    
    public UpdateCartItemRequest(UUID cartItemId, int quantity) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
    }
    
    public UUID getCartItemId() {
        return cartItemId;
    }
    
    public void setCartItemId(UUID cartItemId) {
        this.cartItemId = cartItemId;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
