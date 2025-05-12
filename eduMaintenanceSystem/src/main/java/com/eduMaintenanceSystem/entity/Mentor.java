package com.eduMaintenanceSystem.entity;

import jakarta.persistence.*;
import lombok.*;
import com.eduMaintenanceSystem.enums.Role;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mentor")
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private String expertise; // Courses they can teach

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.MENTOR; // Default role for mentor
}