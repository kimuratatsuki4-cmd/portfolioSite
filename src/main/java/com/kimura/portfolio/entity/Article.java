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

    // Twitter風UIのために、アイコン画像のパスやユーザーIDっぽいものがあると良い
    private String authorId; // e.g. @kate_dev

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // いいね数など（ダミー用）
    private int likes;
    private int retweets;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.likes == 0)
            this.likes = (int) (Math.random() * 100);
        if (this.retweets == 0)
            this.retweets = (int) (Math.random() * 50);
    }
}
