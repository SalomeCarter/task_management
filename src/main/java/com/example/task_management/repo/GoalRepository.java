package com.example.task_management.repo;

import com.example.task_management.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByUserId(long id);

    Optional<Goal> findByName(String name);
    List<Goal> findById(long id);


}
