package com.project.backend.service;

import com.project.backend.entity.Category;
import com.project.backend.entity.ProductAttribute;
import com.project.backend.repository.CategoryRepository;
import com.project.backend.repository.ProductAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FilterOptionsService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private ProductAttributeRepository productAttributeRepository;
    
    @Transactional(readOnly = true)
    public FilterOptions getAvailableFilters() {
        List<Category> categories = categoryRepository.findAllActive();
        List<ProductAttribute> sizes = productAttributeRepository.findSizeAttributesActive();
        List<ProductAttribute> colors = productAttributeRepository.findColorAttributesActive();
        
        List<String> categoryNames = categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());
        
        List<String> sizeValues = sizes.stream()
                .map(ProductAttribute::getValue)
                .collect(Collectors.toList());
        
        List<String> colorValues = colors.stream()
                .map(ProductAttribute::getValue)
                .collect(Collectors.toList());
        
        List<String> priceRanges = List.of(
            "0-50", "50-100", "100-200", "200-500", "500+"
        );
        
        return new FilterOptions(categoryNames, priceRanges, sizeValues, colorValues);
    }
    
    @Transactional(readOnly = true)
    public List<String> getCategoryOptions() {
        return categoryRepository.findAllActive().stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<String> getSizeOptions() {
        return productAttributeRepository.findSizeAttributesActive().stream()
                .map(ProductAttribute::getValue)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<String> getColorOptions() {
        return productAttributeRepository.findColorAttributesActive().stream()
                .map(ProductAttribute::getValue)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<String> getPriceRangeOptions() {
        return List.of("0-50", "50-100", "100-200", "200-500", "500+");
    }
    
    // Inner class for filter options
    public static class FilterOptions {
        private List<String> categories;
        private List<String> priceRanges;
        private List<String> sizes;
        private List<String> colors;
        
        public FilterOptions(List<String> categories, List<String> priceRanges, List<String> sizes, List<String> colors) {
            this.categories = categories;
            this.priceRanges = priceRanges;
            this.sizes = sizes;
            this.colors = colors;
        }
        
        public List<String> getCategories() { return categories; }
        public void setCategories(List<String> categories) { this.categories = categories; }
        
        public List<String> getPriceRanges() { return priceRanges; }
        public void setPriceRanges(List<String> priceRanges) { this.priceRanges = priceRanges; }
        
        public List<String> getSizes() { return sizes; }
        public void setSizes(List<String> sizes) { this.sizes = sizes; }
        
        public List<String> getColors() { return colors; }
        public void setColors(List<String> colors) { this.colors = colors; }
    }
}
