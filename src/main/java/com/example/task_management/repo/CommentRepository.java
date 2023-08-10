package com.example.task_management.repo;

import com.example.task_management.entity.Comment;
import com.example.task_management.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTask(Task task);

}
