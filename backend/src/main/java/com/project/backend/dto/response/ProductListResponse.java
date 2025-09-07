package com.project.backend.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public class ProductListResponse {
    
    private List<ProductDTO> products;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean isFirst;
    private boolean isLast;
    
    public ProductListResponse() {}
    
    public ProductListResponse(Page<ProductDTO> page) {
        this.products = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber();
        this.pageSize = page.getSize();
        this.hasNext = page.hasNext();
        this.hasPrevious = page.hasPrevious();
        this.isFirst = page.isFirst();
        this.isLast = page.isLast();
    }
    
    public List<ProductDTO> getProducts() {
        return products;
    }
    
    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
    
    public long getTotalElements() {
        return totalElements;
    }
    
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
    
    public int getTotalPages() {
        return totalPages;
    }
    
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    
    public int getCurrentPage() {
        return currentPage;
    }
    
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public boolean isHasNext() {
        return hasNext;
    }
    
    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
    
    public boolean isHasPrevious() {
        return hasPrevious;
    }
    
    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }
    
    public boolean isFirst() {
        return isFirst;
    }
    
    public void setFirst(boolean first) {
        isFirst = first;
    }
    
    public boolean isLast() {
        return isLast;
    }
    
    public void setLast(boolean last) {
        isLast = last;
    }
}
