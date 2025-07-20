package com.example.bookingsoccerfield.controller;

import com.example.bookingsoccerfield.models.dto.UserRequestDTO;
import com.example.bookingsoccerfield.models.dto.UserResponseDTO;
import com.example.bookingsoccerfield.models.entity.User;
import com.example.bookingsoccerfield.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDTO>> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String role
    ) {
        return ResponseEntity.ok(userService.searchUsers(name, email, role));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<UserResponseDTO> updateStatus(@PathVariable Long id, @RequestParam boolean enabled) {
        return ResponseEntity.ok(userService.updateStatus(id, enabled));
    }
}
