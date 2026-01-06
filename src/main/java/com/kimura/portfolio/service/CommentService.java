package com.kimura.portfolio.service;

import com.kimura.portfolio.entity.Comment;
import com.kimura.portfolio.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional // トランザクション管理を有効にする
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // 最新3件のコメントを取得する
    @Transactional(readOnly = true) // 読み取り専用にすることでパフォーマンス最適化
    public List<Comment> getLatestComments() {
        return commentRepository.findTop3ByOrderByCreatedAtDesc();
    }

    // コメントを保存する
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
