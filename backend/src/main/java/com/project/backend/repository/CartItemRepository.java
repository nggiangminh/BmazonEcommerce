package com.project.backend.repository;

import com.project.backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    
    // Find cart items by cart ID
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId")
    List<CartItem> findByCartId(@Param("cartId") UUID cartId);
    
    // Find cart items by cart ID with product and SKU details
    @Query("SELECT ci FROM CartItem ci LEFT JOIN FETCH ci.product LEFT JOIN FETCH ci.productSku WHERE ci.cart.id = :cartId")
    List<CartItem> findByCartIdWithDetails(@Param("cartId") UUID cartId);
    
    // Find cart item by cart ID and product SKU ID
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.productSku.id = :productSkuId")
    Optional<CartItem> findByCartIdAndProductSkuId(@Param("cartId") UUID cartId, @Param("productSkuId") UUID productSkuId);
    
    // Find cart item by cart ID and product ID
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId")
    List<CartItem> findByCartIdAndProductId(@Param("cartId") UUID cartId, @Param("productId") UUID productId);
    
    // Count cart items by cart ID
    @Query("SELECT COUNT(ci) FROM CartItem ci WHERE ci.cart.id = :cartId")
    long countByCartId(@Param("cartId") UUID cartId);
    
    // Sum total quantity by cart ID
    @Query("SELECT COALESCE(SUM(ci.quantity), 0) FROM CartItem ci WHERE ci.cart.id = :cartId")
    int sumQuantityByCartId(@Param("cartId") UUID cartId);
    
    // Delete cart items by cart ID
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteByCartId(@Param("cartId") UUID cartId);
    
    // Find cart items by user ID
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.user.id = :userId")
    List<CartItem> findByUserId(@Param("userId") UUID userId);
    
    // Find cart items by user ID with details
    @Query("SELECT ci FROM CartItem ci LEFT JOIN FETCH ci.product LEFT JOIN FETCH ci.productSku WHERE ci.cart.user.id = :userId")
    List<CartItem> findByUserIdWithDetails(@Param("userId") UUID userId);
}
