package com.example.bookingsoccerfield.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fields")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private FieldType type; // VD: 5vs5, 7vs7

    private Double pricePerHour;

    private String location;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum FieldType {
        SEVEN_VS_SEVEN, ELEVEN_VS_ELEVEN
    }

    public enum Status {
        AVAILABLE, MAINTENANCE
    }
}
