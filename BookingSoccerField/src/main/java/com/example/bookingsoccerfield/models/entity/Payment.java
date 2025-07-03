package com.example.bookingsoccerfield.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id", unique = true)
    private Booking booking;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private Method method; // MOMO, VNPay, CASH,...

    @Enumerated(EnumType.STRING)
    private Status status; // PAID, UNPAID, FAILED

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Method {
         ZALOPAY, VNPAY
    }

    public enum Status {
        PAID, UNPAID, FAILED
    }
}
