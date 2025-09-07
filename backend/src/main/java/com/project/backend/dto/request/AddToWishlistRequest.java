package com.project.backend.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AddToWishlistRequest {
    
    @NotNull(message = "Product ID is required")
    private UUID productId;
    
    public AddToWishlistRequest() {}
    
    public AddToWishlistRequest(UUID productId) {
        this.productId = productId;
    }
    
    public UUID getProductId() {
        return productId;
    }
    
    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
