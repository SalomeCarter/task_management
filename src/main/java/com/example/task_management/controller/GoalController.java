package com.example.task_management.controller;

import com.example.task_management.entity.Goal;
import com.example.task_management.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/goals")
public class GoalController {
    @Autowired
    private GoalService goalService;
    @PostMapping("/add-goal")
    public String saveGoal(@ModelAttribute Goal goal) {
        goalService.save(goal);
        String goalName = goal.getName();
        return "redirect:/goals/" + goalName;
    }

    @GetMapping("/add-goal")
    public String showGoalForm(Model model) {
        Goal goal = new Goal();
        model.addAttribute("goal", goal);
        return "add-goal";
    }


    @GetMapping
    public String listGoals(Model model) {
        List<Goal> goalslist = goalService.getAllGoals();
        model.addAttribute("goals", goalslist);
        return "goalsList";
    }


    @PostMapping("/{goalName}")
    public String deleteGoal(@PathVariable String goalName) {
        Goal goal = goalService.findByGoalName(goalName);
        if (goal != null) {
            goalService.delete(goal);
        }
        return "goalsList";
    }

}
