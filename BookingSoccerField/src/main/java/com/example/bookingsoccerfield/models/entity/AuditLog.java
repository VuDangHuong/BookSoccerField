package com.example.bookingsoccerfield.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ai thực hiện hành động
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String action;       // Ví dụ: "BOOKING_CREATED", "PAYMENT_FAILED"

    private String description;  // Ghi chú chi tiết nếu có

    private LocalDateTime timestamp = LocalDateTime.now();
}
