package com.kimura.portfolio.repository;

import com.kimura.portfolio.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    // 新しい順に取得
    List<Article> findAllByOrderByCreatedAtDesc();
}
