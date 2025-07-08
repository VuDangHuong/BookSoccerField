package com.example.bookingsoccerfield.service;

import com.example.bookingsoccerfield.models.dto.AuthRequest;
import com.example.bookingsoccerfield.models.dto.AuthResponse;
import com.example.bookingsoccerfield.models.entity.PasswordResetToken;
import com.example.bookingsoccerfield.models.entity.User;
import com.example.bookingsoccerfield.repository.PasswordResetTokenRepository;
import com.example.bookingsoccerfield.repository.UserRepository;
import com.example.bookingsoccerfield.config.security.JwtService;
import com.example.bookingsoccerfield.exception.AuthException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, PasswordResetTokenRepository tokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
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
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException("Email không tồn tại"));

        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(15);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setEmail(email);
        resetToken.setToken(token);
        resetToken.setExpiryDate(expiry);
        tokenRepository.save(resetToken);

        // Gửi email
        emailService.sendPasswordResetEmail(email, token);
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new AuthException("Token không hợp lệ"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new AuthException("Token đã hết hạn");
        }

        User user = userRepository.findByEmail(resetToken.getEmail())
                .orElseThrow(() -> new AuthException("Người dùng không tồn tại"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(resetToken);
    }

}
