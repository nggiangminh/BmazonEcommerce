package com.project.backend.controller;

import com.project.backend.dto.response.CategoryDTO;
import com.project.backend.entity.Category;
import com.project.backend.mapper.CategoryMapper;
import com.project.backend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category Management", description = "APIs for managing categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private CategoryMapper categoryMapper;
    
    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieve all active categories")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved categories"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDTO> categoryDTOs = categoryMapper.toDTOList(categories);
        return ResponseEntity.ok(categoryDTOs);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Retrieve a specific category by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved category"),
        @ApiResponse(responseCode = "404", description = "Category not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable UUID id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            CategoryDTO categoryDTO = categoryMapper.toDTO(category.get());
            return ResponseEntity.ok(categoryDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search categories by name", description = "Search categories by name")
    public ResponseEntity<List<CategoryDTO>> searchCategories(
            @Parameter(description = "Search term") @RequestParam String name) {
        
        List<Category> categories = categoryService.searchCategoriesByName(name);
        List<CategoryDTO> categoryDTOs = categoryMapper.toDTOList(categories);
        
        return ResponseEntity.ok(categoryDTOs);
    }
    
    @GetMapping("/stats")
    @Operation(summary = "Get category statistics", description = "Get category count statistics")
    public ResponseEntity<CategoryStats> getCategoryStats() {
        long totalCategories = categoryService.getTotalCategoryCount();
        return ResponseEntity.ok(new CategoryStats(totalCategories));
    }
    
    // Inner class for statistics
    public static class CategoryStats {
        private long totalCategories;
        
        public CategoryStats(long totalCategories) {
            this.totalCategories = totalCategories;
        }
        
        public long getTotalCategories() {
            return totalCategories;
        }
        
        public void setTotalCategories(long totalCategories) {
            this.totalCategories = totalCategories;
        }
    }
}
