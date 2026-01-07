package com.kimura.portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final com.kimura.portfolio.service.CommentService commentService;

    private final com.kimura.portfolio.service.GithubService githubService;
    private final com.kimura.portfolio.service.ArticleService articleService;

    public PageController(com.kimura.portfolio.service.CommentService commentService,
            com.kimura.portfolio.service.GithubService githubService,
            com.kimura.portfolio.service.ArticleService articleService) {
        this.commentService = commentService;
        this.githubService = githubService;
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String index(org.springframework.ui.Model model) {
        model.addAttribute("latestComments", commentService.getLatestComments());
        // トップページは最新4件だけを表示
        model.addAttribute("articles", articleService.getArticles(false));
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/skills")
    public String skills() {
        return "skills";
    }

    @GetMapping("/works")
    public String works(org.springframework.ui.Model model) {
        model.addAttribute("works", githubService.getRepositories());
        return "works";
    }
}
