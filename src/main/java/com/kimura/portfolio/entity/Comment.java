package com.kimura.portfolio.entity;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name; // Default will be handled in Controller or here contextually, but requirement
                         // says "Default '名無しさん'" in DB explanation.
                         // Actually DB requirement says DEFAULT '名無しさん', but JPA doesn't always strictly
                         // enforce DB defaults unless columnDefinition is used or prePersist.
                         // I will handle "empty input -> '名無しさん'" logic in Controller/Service or
                         // PrePersist.

    @NotBlank(message = "コメント内容を入力してください")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.name == null || this.name.trim().isEmpty()) {
            this.name = "名無しさん";
        }
    }
}
