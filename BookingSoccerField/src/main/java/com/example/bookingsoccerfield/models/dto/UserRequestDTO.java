package com.example.bookingsoccerfield.models.dto;

import com.example.bookingsoccerfield.models.entity.User;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String password;
    private User.Role role;
    private boolean enabled = true;
}
