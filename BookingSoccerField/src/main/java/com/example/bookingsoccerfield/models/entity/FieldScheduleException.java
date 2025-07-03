package com.example.bookingsoccerfield.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "field_schedule_exceptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldScheduleException {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Sân nào bị nghỉ
    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    private LocalDate date;      // Ngày sân không hoạt động

    private String reason;       // Lý do như "Bảo trì", "Sự kiện sinh viên"
}
