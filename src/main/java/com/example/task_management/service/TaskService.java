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

@Transactional
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getTasksByStatus(Status status) {
        return taskRepository.findByStatusIs(status);
    }

    public List<Task> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriorityIs(priority);
    }

    public List<Task> getTasksByDateAscending(LocalDate createdAt) {
        return taskRepository.findByCreatedAtOrderByCreatedAtAsc(createdAt);
    }

    public List<Task> getTasksByDateDescending(LocalDate createdAt) {
        return taskRepository.findByCreatedAtOrderByCreatedAtDesc(createdAt);
    }


    public void save(Task task) {
        taskRepository.save(task);
    }

    public List<Task> findTasksByGoalname(String name) {
        return taskRepository.findByGoalName(name);
    }

    public void delete(Task task) {
        taskRepository.delete(task);
    }

    public Optional<Task> findById(long id){
        return taskRepository.findById(id);
    }


}
