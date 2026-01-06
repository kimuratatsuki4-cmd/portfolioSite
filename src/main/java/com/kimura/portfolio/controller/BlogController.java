package com.kimura.portfolio.controller;

import com.kimura.portfolio.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final ArticleService articleService;

    public BlogController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String blog(Model model) {
        model.addAttribute("articles", articleService.getAllArticles());
        return "blog";
    }
}
