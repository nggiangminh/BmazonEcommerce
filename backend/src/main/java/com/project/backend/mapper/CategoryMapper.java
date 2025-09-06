package com.project.backend.mapper;

import com.project.backend.dto.response.CategoryDTO;
import com.project.backend.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    
    public CategoryDTO toDTO(Category category) {
        return new CategoryDTO(category);
    }
    
    public List<CategoryDTO> toDTOList(List<Category> categories) {
        return categories.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
