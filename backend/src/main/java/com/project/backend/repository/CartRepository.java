package com.project.backend.repository;

import com.project.backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    
    // Find cart by user ID
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    Optional<Cart> findByUserId(@Param("userId") UUID userId);
    
    // Find cart by user ID with cart items
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.cartItems ci LEFT JOIN FETCH ci.product LEFT JOIN FETCH ci.productSku WHERE c.user.id = :userId")
    Optional<Cart> findByUserIdWithItems(@Param("userId") UUID userId);
    
    // Check if cart exists for user
    @Query("SELECT COUNT(c) > 0 FROM Cart c WHERE c.user.id = :userId")
    boolean existsByUserId(@Param("userId") UUID userId);
    
    // Count total carts
    @Query("SELECT COUNT(c) FROM Cart c")
    long countAllCarts();
    
    // Count active carts (with items)
    @Query("SELECT COUNT(c) FROM Cart c WHERE SIZE(c.cartItems) > 0")
    long countActiveCarts();
}
