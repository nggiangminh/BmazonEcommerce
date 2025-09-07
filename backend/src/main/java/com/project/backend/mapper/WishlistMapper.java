package com.project.backend.mapper;

import com.project.backend.dto.response.WishlistDTO;
import com.project.backend.entity.Wishlist;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WishlistMapper {
    
    public WishlistDTO toDTO(Wishlist wishlist) {
        return new WishlistDTO(wishlist);
    }
    
    public List<WishlistDTO> toDTOList(List<Wishlist> wishlists) {
        return wishlists.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
