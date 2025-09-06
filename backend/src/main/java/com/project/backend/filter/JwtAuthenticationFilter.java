package com.project.backend.filter;

import com.project.backend.service.CustomUserDetailsService;
import com.project.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Kiểm tra xem có Authorization header không và có bắt đầu bằng "Bearer " không
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Lấy JWT token từ header (bỏ "Bearer " prefix)
        jwt = authHeader.substring(7);
        
        try {
            // Extract username từ JWT token
            username = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            // Nếu không thể extract username, tiếp tục filter chain
            filterChain.doFilter(request, response);
            return;
        }

        // Nếu có username và chưa có authentication context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            
            // Validate JWT token với user details
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Tạo authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                
                // Set authentication details
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                
                // Set authentication vào SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // Tiếp tục filter chain
        filterChain.doFilter(request, response);
    }
}
