package com.kimura.portfolio.service;

import com.kimura.portfolio.entity.Article;
import com.kimura.portfolio.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Transactional(readOnly = true)
    public List<Article> getArticles(boolean showAll) {
        if (showAll) {
            return articleRepository.findAllByOrderByCreatedAtDesc();
        } else {
            return articleRepository.findTop4ByOrderByCreatedAtDesc();
        }
    }

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }
}
