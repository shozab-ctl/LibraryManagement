package com.management.library.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

// User.java
@Entity
@Data
@Table(name = "userLib")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String firstName;
    private LocalDate memberSince;
    private LocalDate memberTill;
    private String gender;
}

