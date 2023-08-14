package com.example.task_management.service;

import com.example.task_management.entity.Task;
import com.example.task_management.enums.Priority;
import com.example.task_management.enums.Status;
import com.example.task_management.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class TaskService {
    private static final Logger log = Logger.getLogger(TaskService.class.getName());

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getTasksByStatus(Status status) {
        List<Task> tasks = taskRepository.findByStatusIs(status);
        return tasks;
    }

    public List<Task> getTasksByPriority(Priority priority) {
        List<Task> tasks = taskRepository.findByPriorityIs(priority);
        return tasks;
    }

    public List<Task> getTasksByDateAscending(LocalDate createdAt) {
        List<Task> tasks = taskRepository.findByCreatedAtOrderByCreatedAtAsc(createdAt);
        return tasks;
    }

    public List<Task> getTasksByDateDescending(LocalDate createdAt) {
        List<Task> tasks = taskRepository.findByCreatedAtOrderByCreatedAtDesc(createdAt);
        return tasks;
    }

    public void save(Task task) {
        taskRepository.save(task);
        log.log(Level.INFO, "Task saved " + task.getHeader());
    }

    public List<Task> findTasksByGoalname(String name) {
        List<Task> tasks = taskRepository.findByGoalName(name);
        return tasks;
    }

    public void delete(Task task) {
        taskRepository.delete(task);
        log.log(Level.INFO, "Task deleted " + task.getHeader());
    }

    public Optional<Task> updateTask(Task updatedTask) {
        Optional<Task> byId = taskRepository.findById(updatedTask.getId());
        if (byId.isPresent()) {
            taskRepository.save(updatedTask);
            return Optional.of(updatedTask);
        }
        return Optional.empty();
    }

    public Optional<Task> findById(long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        return taskOptional;
    }
}

