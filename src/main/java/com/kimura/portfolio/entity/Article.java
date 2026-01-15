package com.kimura.portfolio.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@Data
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String authorName;

    private String authorId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    private int likes;
    private int retweets;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();

    }
}
