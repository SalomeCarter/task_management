package com.example.task_management.controller;

import com.example.task_management.entity.Goal;
import com.example.task_management.entity.Task;
import com.example.task_management.enums.Priority;
import com.example.task_management.enums.Status;
import com.example.task_management.service.GoalService;
import com.example.task_management.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/goals")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private GoalService goalService;


    @PostMapping("/{goalName}/add-task") //тут должна быть ссылка на форму для сохранения таски?
    public String saveTask(
            @PathVariable String goalName,
            @ModelAttribute Task task) {
        Goal goal = goalService.findByGoalName(goalName);
        if (goal != null) {
            task.setGoal(goal);
            taskService.save(task);
        }
        return "tasksList";
    }



    @GetMapping("/{goalName}/{taskId}")
    public String findTaskById(@PathVariable String goalName, @PathVariable Long taskId, Model model) {
        Optional<Task> taskOptional = taskService.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            model.addAttribute("task", task);
            return "task";
        } else {
            return "taskNotFound";
        }
    }



    @GetMapping("/{goalName}/status/{status}")
    public String getTasksByStatus(@PathVariable String goalName, @PathVariable Status status, Model model) {
        List<Task> tasks = taskService.getTasksByStatus(status);

        model.addAttribute("tasks", tasks);
        model.addAttribute("status", status);

        return "tasks-by-status"; // Нужно делать отдельную html страницу по фильтр статуса?

    }

    @GetMapping("/{goalName}/priority/{priority}")
    public String getTasksByPriority(@PathVariable String goalName, @PathVariable Priority priority, Model model) {
        List<Task> tasks = taskService.getTasksByPriority(priority);

        model.addAttribute("tasks", tasks);
        model.addAttribute("priority", priority);

        return "tasks-by-priority"; //тут должна быть ссылка на форму для сохранения таски?
    }


    @GetMapping("/{goalName}/date-asc/{createdAt}")
    public String getTasksByDateAscending(@PathVariable String goalName, @PathVariable LocalDate createdAt, Model model) {
        List<Task> tasks = taskService.getTasksByDateAscending(createdAt);

        model.addAttribute("tasks", tasks);
        model.addAttribute("createdAt", createdAt);

        return "tasks-by-date-asc"; //тут должна быть ссылка на форму для сохранения таски?
    }

    @GetMapping("/{goalName}/date-desc/{createdAt}")
    public String getTasksByDateDescending(@PathVariable String goalName, @PathVariable LocalDate createdAt, Model model) {
        List<Task> tasks = taskService.getTasksByDateDescending(createdAt);

        model.addAttribute("tasks", tasks);
        model.addAttribute("createdAt", createdAt);

        return "tasks-by-date-desc"; //тут должна быть ссылка на форму для сохранения таски?
    }



    @PostMapping("/{goalName}/{taskId}")
    public String deleteTask(@PathVariable String goalName, @PathVariable Long taskId) {
        Optional<Task> taskOptional = taskService.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            taskService.delete(task);
            return "tasksList";
        } else {
            return "taskNotFound";
        }
    }


}
