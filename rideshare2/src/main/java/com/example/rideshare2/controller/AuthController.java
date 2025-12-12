package com.example.rideshare2.controller;

import com.example.rideshare2.model.AuthRequest;
import com.example.rideshare2.model.User;
import com.example.rideshare2.service.UserService;
import com.example.rideshare2.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager manager;
    private final UserService service;
    private final JwtUtil jwt;

    public AuthController(AuthenticationManager m, UserService s, JwtUtil j) {
        this.manager = m; this.service = s; this.jwt = j;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user.getUsername(), user.getPassword(), user.getRole());
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthRequest req) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        String token = jwt.generateToken(req.getUsername(), service.loadUserByUsername(req.getUsername()).getAuthorities().iterator().next().getAuthority());
        return Map.of("token", token);
    }
}
