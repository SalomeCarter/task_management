package com.example.task_management.service;

import com.example.task_management.entity.Comment;
import com.example.task_management.entity.Task;
import com.example.task_management.repo.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getCommentsByTask(Task task) {
        return commentRepository.findByTask(task);
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

}
