package com.example.bookingsoccerfield.models.dto;

import com.example.bookingsoccerfield.models.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private User.Role role;
    private boolean enabled;
    private LocalDateTime createdAt;
}
