package com.project.backend.service;

import com.project.backend.dto.request.createUserRequest;
import com.project.backend.dto.request.updateUserRequest;
import com.project.backend.dto.response.UserDTO;
import com.project.backend.entity.User;
import com.project.backend.repository.UserRepository;
import com.project.backend.exception.ResourceNotFoundException;
import com.project.backend.exception.DuplicateResourceException;
import com.project.backend.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDTO createUser(createUserRequest request) {
        if (userRepository.existsByUsernameAndNotDeleted(request.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        if (userRepository.existsByEmailAndNotDeleted(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(request.getPassword());

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsernameAndNotDeleted(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAllNotDeleted(pageable);
        return users.map(userMapper::toDTO);
    }

    @Override
    public UserDTO updateUser(UUID id, updateUserRequest request) {
        User user = userRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsernameAndNotDeleted(request.getUsername())) {
                throw new DuplicateResourceException("Username already exists");
            }
        }

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmailAndNotDeleted(request.getEmail())) {
                throw new DuplicateResourceException("Email already exists");
            }
        }

        userMapper.updateEntityFromRequest(request, user);
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    @Override
    public void deleteUser(UUID id) {
        User user = userRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Soft delete
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsernameAndNotDeleted(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmailAndNotDeleted(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isCurrentUser(UUID userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsernameAndNotDeleted(currentUsername)
                .orElse(null);
        
        return currentUser != null && currentUser.getId().equals(userId);
    }
}
