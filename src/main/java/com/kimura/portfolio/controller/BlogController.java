package com.kimura.portfolio.controller;

import com.kimura.portfolio.service.ArticleService;
import com.kimura.portfolio.entity.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final ArticleService articleService;

    public BlogController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String blog(
            @org.springframework.web.bind.annotation.RequestParam(name = "showAll", defaultValue = "false") boolean showAll,
            Model model) {
        System.out.println("Requested blog with showAll=" + showAll);
        model.addAttribute("articles", articleService.getArticles(showAll));
        model.addAttribute("showAll", showAll);
        return "blog";
    }

    @PostMapping("/post")
    public String postArticle(@RequestParam("title") String title, @RequestParam("content") String content) {
        Article article = new Article();
        article.setContent(content);
        article.setTitle(title);
        article.setAuthorName("Kate");
        article.setAuthorId("@kate_dev");
        articleService.saveArticle(article);
        return "redirect:/blog";
    }
}
