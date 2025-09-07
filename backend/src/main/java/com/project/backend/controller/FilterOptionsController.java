package com.project.backend.controller;

import com.project.backend.service.FilterOptionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filters")
@Tag(name = "Filter Options", description = "APIs for getting available filter options")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class FilterOptionsController {
    
    @Autowired
    private FilterOptionsService filterOptionsService;
    
    @GetMapping
    @Operation(summary = "Get all filter options", description = "Get all available filter options for products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Filter options retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<FilterOptionsService.FilterOptions> getAllFilterOptions() {
        FilterOptionsService.FilterOptions options = filterOptionsService.getAvailableFilters();
        return ResponseEntity.ok(options);
    }
    
    @GetMapping("/categories")
    @Operation(summary = "Get category options", description = "Get available category filter options")
    public ResponseEntity<List<String>> getCategoryOptions() {
        List<String> categories = filterOptionsService.getCategoryOptions();
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/sizes")
    @Operation(summary = "Get size options", description = "Get available size filter options")
    public ResponseEntity<List<String>> getSizeOptions() {
        List<String> sizes = filterOptionsService.getSizeOptions();
        return ResponseEntity.ok(sizes);
    }
    
    @GetMapping("/colors")
    @Operation(summary = "Get color options", description = "Get available color filter options")
    public ResponseEntity<List<String>> getColorOptions() {
        List<String> colors = filterOptionsService.getColorOptions();
        return ResponseEntity.ok(colors);
    }
    
    @GetMapping("/price-ranges")
    @Operation(summary = "Get price range options", description = "Get available price range filter options")
    public ResponseEntity<List<String>> getPriceRangeOptions() {
        List<String> priceRanges = filterOptionsService.getPriceRangeOptions();
        return ResponseEntity.ok(priceRanges);
    }
}
