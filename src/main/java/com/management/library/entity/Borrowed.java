package com.management.library.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

// Borrowed.java
@Entity
@Data
public class Borrowed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User borrower;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private LocalDate borrowedFrom;
    private LocalDate borrowedTo;
}
