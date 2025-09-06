package com.project.backend.service;

import com.project.backend.dto.request.createUserRequest;
import com.project.backend.dto.request.updateUserRequest;
import com.project.backend.dto.response.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface UserService {
    UserDTO createUser(createUserRequest request);

    UserDTO getUserById(UUID id);

    UserDTO getUserByUsername(String username);

    UserDTO getUserByEmail(String email);

    Page<UserDTO> getAllUsers(Pageable pageable);

    UserDTO updateUser(UUID id, updateUserRequest request);

    void deleteUser(UUID id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
