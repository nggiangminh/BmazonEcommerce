package com.project.backend.mapper;

import com.project.backend.dto.response.CartDTO;
import com.project.backend.entity.Cart;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {
    
    public CartDTO toDTO(Cart cart) {
        return new CartDTO(cart);
    }
    
    public List<CartDTO> toDTOList(List<Cart> carts) {
        return carts.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
