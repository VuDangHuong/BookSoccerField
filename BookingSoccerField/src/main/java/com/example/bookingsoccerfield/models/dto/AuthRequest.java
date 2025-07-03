package com.example.bookingsoccerfield.models.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
    private String name;
    private String phone;

    private String messages;
    public AuthRequest(String email, String password, String name, String phone, String messages) {
        this.email = email;
        this.password= password;
        this.name = name;
        this.phone = phone;
        this.messages=messages;

    }
}
