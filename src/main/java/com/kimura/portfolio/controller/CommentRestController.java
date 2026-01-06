package com.kimura.portfolio.controller;

import com.kimura.portfolio.entity.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {

    private final com.kimura.portfolio.service.CommentService commentService;

    @Autowired
    public CommentRestController(com.kimura.portfolio.service.CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Comment savedComment = commentService.saveComment(comment);
        return ResponseEntity.ok(savedComment);
    }
}
