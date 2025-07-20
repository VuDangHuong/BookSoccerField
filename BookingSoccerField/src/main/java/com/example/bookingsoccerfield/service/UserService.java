package com.example.bookingsoccerfield.service;

import com.example.bookingsoccerfield.config.security.JwtService;
import com.example.bookingsoccerfield.models.dto.UserRequestDTO;
import com.example.bookingsoccerfield.models.dto.UserResponseDTO;
import com.example.bookingsoccerfield.models.entity.User;
import com.example.bookingsoccerfield.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
//    private final UserRepository userRepository;
    private final JwtService jwtService;
//
    public User getCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return userRepository.findByEmail(email).orElse(null);
    }
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        user.setEnabled(dto.isEnabled());
        user.setCreatedAt(LocalDateTime.now());
        return toResponseDTO(userRepository.save(user));
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        user.setEnabled(dto.isEnabled());
        return toResponseDTO(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public List<UserResponseDTO> searchUsers(String name, String email, String roleStr) {
        User.Role role = null;
        if (roleStr != null && !roleStr.isBlank()) {
            role = User.Role.valueOf(roleStr.toUpperCase());
        }
        return userRepository.searchUsers(name, email, role)
                .stream().map(this::toResponseDTO).toList();
    }

    public UserResponseDTO updateStatus(Long id, boolean enabled) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(enabled);
        return toResponseDTO(userRepository.save(user));
    }

    private UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setEnabled(user.isEnabled());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
