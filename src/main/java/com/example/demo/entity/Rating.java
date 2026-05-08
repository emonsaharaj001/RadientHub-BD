package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int rating;

    private String review;

    // Getter Setter
}