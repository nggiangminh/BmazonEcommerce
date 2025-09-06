package com.project.backend.controller;

import com.project.backend.dto.response.UserDTO;
import com.project.backend.entity.User;
import com.project.backend.enums.Role;
import com.project.backend.repository.UserRepository;
import com.project.backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
@Tag(name = "Admin Management", description = "APIs for admin-only operations")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    @Operation(summary = "Get all users with admin details", description = "Retrieves a paginated list of all users with additional admin information")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - Admin role required")
    })
    public ResponseEntity<Page<UserDTO>> getAllUsersWithAdminDetails(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<UserDTO> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{id}/role")
    @Operation(summary = "Update user role", description = "Updates a user's role (Admin only)")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User role updated successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - Admin role required"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid role")
    })
    public ResponseEntity<UserDTO> updateUserRole(
            @Parameter(description = "User ID", required = true) @PathVariable UUID id,
            @Parameter(description = "New role", required = true) @RequestParam Role role) {
        
        User user = userRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setRole(role);
        User updatedUser = userRepository.save(user);
        
        // Convert to DTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(updatedUser.getId());
        userDTO.setFirstName(updatedUser.getFirstName());
        userDTO.setLastName(updatedUser.getLastName());
        userDTO.setUsername(updatedUser.getUsername());
        userDTO.setEmail(updatedUser.getEmail());
        userDTO.setRole(updatedUser.getRole());
        userDTO.setCreateAt(updatedUser.getCreatedAt());
        
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/users/statistics")
    @Operation(summary = "Get user statistics", description = "Retrieves user statistics for admin dashboard")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - Admin role required")
    })
    public ResponseEntity<UserStatistics> getUserStatistics() {
        long totalUsers = userRepository.countByDeletedAtIsNull();
        long adminUsers = userRepository.countByRoleAndDeletedAtIsNull(Role.ADMIN);
        long regularUsers = userRepository.countByRoleAndDeletedAtIsNull(Role.USER);
        
        UserStatistics stats = new UserStatistics(totalUsers, adminUsers, regularUsers);
        return ResponseEntity.ok(stats);
    }

    @PostMapping("/users/{id}/activate")
    @Operation(summary = "Activate user", description = "Activates a soft-deleted user (Admin only)")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User activated successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - Admin role required"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> activateUser(
            @Parameter(description = "User ID", required = true) @PathVariable UUID id) {
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setDeletedAt(null);
        User activatedUser = userRepository.save(user);
        
        // Convert to DTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(activatedUser.getId());
        userDTO.setFirstName(activatedUser.getFirstName());
        userDTO.setLastName(activatedUser.getLastName());
        userDTO.setUsername(activatedUser.getUsername());
        userDTO.setEmail(activatedUser.getEmail());
        userDTO.setRole(activatedUser.getRole());
        userDTO.setCreateAt(activatedUser.getCreatedAt());
        
        return ResponseEntity.ok(userDTO);
    }

    // Inner class for statistics
    public static class UserStatistics {
        private long totalUsers;
        private long adminUsers;
        private long regularUsers;

        public UserStatistics(long totalUsers, long adminUsers, long regularUsers) {
            this.totalUsers = totalUsers;
            this.adminUsers = adminUsers;
            this.regularUsers = regularUsers;
        }

        // Getters
        public long getTotalUsers() { return totalUsers; }
        public long getAdminUsers() { return adminUsers; }
        public long getRegularUsers() { return regularUsers; }
    }
}
