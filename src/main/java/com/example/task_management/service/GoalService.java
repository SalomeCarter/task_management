package com.example.task_management.service;

import com.example.task_management.entity.Goal;
import com.example.task_management.repo.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class GoalService {
    private static final Logger log = Logger.getLogger(GoalService.class.getName());

    @Autowired
    private GoalRepository goalRepository;

    public Optional<Goal> updateGoal(Goal updatedGoal) {
        Optional<Goal> byId = goalRepository.findById(updatedGoal.getId());
        if (byId.isPresent()) {
            goalRepository.save(updatedGoal);
            return Optional.of(updatedGoal);
        }
        return Optional.empty();
    }


    public void save(Goal goal) {
        goalRepository.save(goal);
        log.log(Level.INFO, "Goal saved " + goal.getName());
    }

    public List<Goal> findByUser(long id) {
        List<Goal> goals = goalRepository.findByUserId(id);
        return goals;
    }

    public void delete(Goal goal) {
        goalRepository.delete(goal);
        log.log(Level.INFO, "Goal deleted " + goal.getName());
    }

    public List<Goal> getAllGoals() {
        List<Goal> goals = goalRepository.findAll();
        return goals;
    }

    public Goal findByGoalName(String goalName) {
        Optional<Goal> goalOptional = goalRepository.findByName(goalName);
        if (goalOptional.isPresent()) {
            Goal goal = goalOptional.get();
            return goal;
        } else {
            return null;
        }
    }
}

