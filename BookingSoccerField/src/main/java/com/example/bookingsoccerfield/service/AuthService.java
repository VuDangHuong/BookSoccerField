package com.example.bookingsoccerfield.service;

import com.example.bookingsoccerfield.models.dto.AuthRequest;
import com.example.bookingsoccerfield.models.dto.AuthResponse;
import com.example.bookingsoccerfield.models.entity.User;
import com.example.bookingsoccerfield.repository.UserRepository;
import com.example.bookingsoccerfield.config.security.JwtService;
import com.example.bookingsoccerfield.exception.AuthException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    public AuthRequest register(AuthRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException("Email already exists");
        }

        String email = request.getEmail().toLowerCase();
        User.Role role;

        if (email.endsWith("@e.tlu.edu.vn")) {
            role = User.Role.SINH_VIEN;
        } else if (email.endsWith("@tlu.edu.vn")) {
            role = User.Role.GIANG_VIEN;
        } else {
            role = User.Role.KHACH_HANG;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setName(request.getName()); // ✅ Gán đúng tên người dùng
        user.setPhone(request.getPhone());
        userRepository.save(user);

//        String token = jwtService.generateToken(user.getEmail());
        return new AuthRequest(user.getEmail(), user.getPassword(), user.getName(),user.getPhone(),"Dang ki thanh cong");

    }
}
