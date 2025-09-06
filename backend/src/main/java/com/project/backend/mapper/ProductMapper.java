package com.project.backend.mapper;

import com.project.backend.dto.request.CreateProductRequest;
import com.project.backend.dto.request.UpdateProductRequest;
import com.project.backend.dto.response.ProductDTO;
import com.project.backend.entity.Category;
import com.project.backend.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    
    public Product toEntity(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setSummary(request.getSummary());
        product.setCover(request.getCover());
        
        // Set category if categoryId is provided
        if (request.getCategoryId() != null) {
            Category category = new Category();
            category.setId(request.getCategoryId());
            product.setCategory(category);
        }
        
        return product;
    }
    
    public Product toEntity(UpdateProductRequest request, Product existingProduct) {
        if (request.getName() != null) {
            existingProduct.setName(request.getName());
        }
        if (request.getDescription() != null) {
            existingProduct.setDescription(request.getDescription());
        }
        if (request.getSummary() != null) {
            existingProduct.setSummary(request.getSummary());
        }
        if (request.getCover() != null) {
            existingProduct.setCover(request.getCover());
        }
        if (request.getCategoryId() != null) {
            Category category = new Category();
            category.setId(request.getCategoryId());
            existingProduct.setCategory(category);
        }
        
        return existingProduct;
    }
    
    public ProductDTO toDTO(Product product) {
        return new ProductDTO(product);
    }
    
    public List<ProductDTO> toDTOList(List<Product> products) {
        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
