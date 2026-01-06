package com.kimura.portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final com.kimura.portfolio.service.CommentService commentService;

    public PageController(com.kimura.portfolio.service.CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String index(org.springframework.ui.Model model) {
        model.addAttribute("latestComments", commentService.getLatestComments());
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
    public String works() {
        return "works";
    }
}
