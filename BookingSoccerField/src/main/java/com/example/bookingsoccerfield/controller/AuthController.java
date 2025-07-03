package com.example.bookingsoccerfield.controller;

import com.example.bookingsoccerfield.models.dto.AuthRequest;
import com.example.bookingsoccerfield.models.dto.AuthResponse;
import com.example.bookingsoccerfield.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthRequest> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}