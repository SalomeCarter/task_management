package com.example.task_management.repo;

import com.example.task_management.entity.Task;
import com.example.task_management.enums.Priority;
import com.example.task_management.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatusIs(Status status);
    List<Task> findByPriorityIs(Priority priority);
    List<Task> findByCreatedAtOrderByCreatedAtAsc(LocalDate createdAt);

    List<Task> findByCreatedAtOrderByCreatedAtDesc(LocalDate createdAt);

    List<Task> findByGoalName(String goalName);
}
