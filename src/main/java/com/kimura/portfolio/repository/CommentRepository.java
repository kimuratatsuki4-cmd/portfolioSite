package com.kimura.portfolio.repository;

import com.kimura.portfolio.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Find top 3 by created_at desc
    List<Comment> findTop3ByOrderByCreatedAtDesc();
}
