package com.example.rideshare2.config;

import com.example.rideshare2.service.UserService;
import com.example.rideshare2.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1) Read Authorization header
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;

        // 2) Check for Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);   // remove "Bearer "
            try {
                username = jwtUtil.extractUsername(token);
            } catch (Exception ex) {
                // malformed/expired token → just proceed without authentication
                System.out.println("Invalid JWT: " + ex.getMessage());
            }
        }

        // 3) If token valid and user not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails.getUsername())) {

                // 4) Create authentication object
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // 5) Save authentication to security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 6) Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
