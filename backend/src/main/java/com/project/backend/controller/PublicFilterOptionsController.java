package com.project.backend.controller;

import com.project.backend.service.FilterOptionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/filters")
@Tag(name = "Public Filter Options", description = "Public APIs for getting available filter options without authentication")
public class PublicFilterOptionsController {
    
    @Autowired
    private FilterOptionsService filterOptionsService;
    
    @GetMapping
    @Operation(summary = "Get all filter options (Public)", description = "Get all available filter options for products without authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Filter options retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<FilterOptionsService.FilterOptions> getAllFilterOptions() {
        FilterOptionsService.FilterOptions options = filterOptionsService.getAvailableFilters();
        return ResponseEntity.ok(options);
    }
    
    @GetMapping("/categories")
    @Operation(summary = "Get category options (Public)", description = "Get available category filter options without authentication")
    public ResponseEntity<List<String>> getCategoryOptions() {
        List<String> categories = filterOptionsService.getCategoryOptions();
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/sizes")
    @Operation(summary = "Get size options (Public)", description = "Get available size filter options without authentication")
    public ResponseEntity<List<String>> getSizeOptions() {
        List<String> sizes = filterOptionsService.getSizeOptions();
        return ResponseEntity.ok(sizes);
    }
    
    @GetMapping("/colors")
    @Operation(summary = "Get color options (Public)", description = "Get available color filter options without authentication")
    public ResponseEntity<List<String>> getColorOptions() {
        List<String> colors = filterOptionsService.getColorOptions();
        return ResponseEntity.ok(colors);
    }
    
    @GetMapping("/price-ranges")
    @Operation(summary = "Get price range options (Public)", description = "Get available price range filter options without authentication")
    public ResponseEntity<List<String>> getPriceRangeOptions() {
        List<String> priceRanges = filterOptionsService.getPriceRangeOptions();
        return ResponseEntity.ok(priceRanges);
    }
}
