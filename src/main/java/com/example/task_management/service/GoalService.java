package com.example.task_management.service;

import com.example.task_management.entity.Goal;
import com.example.task_management.entity.Task;
import com.example.task_management.repo.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    public void save(Goal goal) {
        goalRepository.save(goal);
    }

    public List<Goal> findByUser(long id){
        return goalRepository.findByUserId(id);
    }

    public void delete(Goal goal) {
        goalRepository.delete(goal);
    }

    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    public Goal findByGoalName(String goalName) {
        Optional<Goal> goalOptional = goalRepository.findByName(goalName);
        return goalOptional.orElse(null);
    }

    //Нужен ли этот метод? Можно просто сделать ссылкой
//    public boolean addTask(long id, Task task) {
//        Goal goal = goalRepository.findById(id).orElse(null);
//        if (goal != null) {
//            goal.getTasks().add(task);
//            goalRepository.save(goal);
//            return true;
//        }
//        return false;
//    }


}
