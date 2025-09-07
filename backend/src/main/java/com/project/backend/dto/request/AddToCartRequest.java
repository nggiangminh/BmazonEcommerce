package com.project.backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AddToCartRequest {
    
    @NotNull(message = "Product SKU ID is required")
    private UUID productSkuId;
    
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity = 1;
    
    public AddToCartRequest() {}
    
    public AddToCartRequest(UUID productSkuId, int quantity) {
        this.productSkuId = productSkuId;
        this.quantity = quantity;
    }
    
    public UUID getProductSkuId() {
        return productSkuId;
    }
    
    public void setProductSkuId(UUID productSkuId) {
        this.productSkuId = productSkuId;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
