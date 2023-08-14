package com.example.task_management.controller;

import com.example.task_management.entity.Attachment;
import com.example.task_management.entity.Comment;
import com.example.task_management.entity.Goal;
import com.example.task_management.entity.Task;
import com.example.task_management.enums.Priority;
import com.example.task_management.enums.Status;
import com.example.task_management.form.CommentForm;
import com.example.task_management.service.AttachmentService;
import com.example.task_management.service.CommentService;
import com.example.task_management.service.GoalService;
import com.example.task_management.service.TaskService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/goals")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private GoalService goalService;
    @Autowired
    private CommentService commentService;

    @Autowired
    private AttachmentService attachmentService;


    @GetMapping("/{goalName}/add-task")
    public String showAddTaskForm(@PathVariable String goalName, Model model) {
        Goal goal = goalService.findByGoalName(goalName);
        if (goal != null) {
            Task task = new Task();
            task.setGoal(goal);
            model.addAttribute("task", task);
            return "add-task";
        } else {
            return "redirect:/goals";
        }
    }

    @PostMapping("/{goalName}/add-task")
    public String saveTask(
            @PathVariable String goalName,
            @ModelAttribute Task task) {
        Goal goal = goalService.findByGoalName(goalName);
        if (goal != null) {
            task.setGoal(goal);
            taskService.save(task);
            return "redirect:/goals/" + goalName;
        } else {
            return "redirect:/goals";
        }
    }

    @GetMapping("/{goalName}/{taskId}")
    public String findTaskById(@PathVariable String goalName, @PathVariable Long taskId, Model model) {
        Optional<Task> taskOptional = taskService.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            List<Comment> comments = commentService.getCommentsByTask(task);
            model.addAttribute("task", task);
            model.addAttribute("comments", comments);
            model.addAttribute("commentForm", new CommentForm());
            return "task";
        } else {
            return "taskNotFound";
        }
    }

    @PostMapping("/{goalName}/{taskId}")
    public String addCommentToTask(
            @PathVariable String goalName,
            @PathVariable Long taskId,
            @ModelAttribute CommentForm commentForm) {

        Optional<Task> taskOptional = taskService.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();

            Comment comment = Comment.builder()
                    .text(commentForm.getText())
                    .createdAt(LocalDateTime.now())
                    .task(task)
                    .build();
            commentService.save(comment);
            return "redirect:/goals/" + goalName + "/" + taskId;
        } else {
            return "taskNotFound";
        }
    }


    @GetMapping("/{goalName}/{taskId}/download/{attachmentId}")
    public void downloadAttachment(
            @PathVariable String goalName,
            @PathVariable Long taskId,
            @PathVariable Long attachmentId,
            HttpServletResponse response) throws IOException {
        Optional<Task> taskOptional = taskService.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            Optional<Attachment> attachmentOptional = task.getAttachments().stream()
                    .filter(attachment -> attachment.getId().equals(attachmentId))
                    .findFirst();
            if (attachmentOptional.isPresent()) {
                Attachment attachment = attachmentOptional.get();
                String resourcePath = "static/download/" + attachment.getFilePath();

                Resource resource = new ClassPathResource(resourcePath);

                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getFileName() + "\"");

                try (InputStream inputStream = resource.getInputStream();
                     OutputStream outputStream = response.getOutputStream()) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            }
        }
    }

    @GetMapping("/{goalName}/{taskId}/upload") public String displayUploadForm(
            @PathVariable String goalName,
            @PathVariable Long taskId) {
        return "task"; //<-- верный ли путь
    }

    @PostMapping("/{goalName}/{taskId}/upload")
    public String addAttachmentToTask(
            @PathVariable String goalName,
            @PathVariable Long taskId,
            @ModelAttribute("attachment") Attachment attachment,
            @RequestParam("file") MultipartFile file) {

        Optional<Task> taskOptional = taskService.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();

            String fileName = file.getOriginalFilename();

            String uploadDir = "static/upload/";
            String filePath = uploadDir + fileName;

            try {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Files.write(Paths.get(filePath), file.getBytes());

                attachment.setFileName(fileName);
                attachment.setFilePath(filePath);
                attachment.setTask(task);

                attachmentService.save(attachment);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "redirect:/goals/" + goalName + "/" + taskId;
        } else {
            return "taskNotFound";
        }
    }


    @GetMapping("/{goalName}")
    public String tasksList(@PathVariable String goalName, Model model) {
        Goal goal = goalService.findByGoalName(goalName);
        if (goal != null) {
            List<Task> tasks = taskService.findTasksByGoalname(goalName);
            model.addAttribute("goal", goal);
            model.addAttribute("tasks", tasks);
            return "tasksList";
        } else {
            return "redirect:/goals";
        }
    }

    @GetMapping("/{goalName}/status/{status}")
    public String getTasksByStatus(@PathVariable String goalName, @PathVariable Status status, Model model) {
        List<Task> tasks = taskService.getTasksByStatus(status);
        model.addAttribute("tasks", tasks);
        model.addAttribute("status", status);
        return "tasksList";
    }

    @GetMapping("/{goalName}/priority/{priority}")
    public String getTasksByPriority(@PathVariable String goalName, @PathVariable Priority priority, Model model) {
        List<Task> tasks = taskService.getTasksByPriority(priority);
        model.addAttribute("tasks", tasks);
        model.addAttribute("priority", priority);
        return "tasksList";
    }

    @GetMapping("/{goalName}/date-asc")
    public String getTasksByDateAscending(
            @PathVariable String goalName,
            @RequestParam LocalDate createdAt,
            Model model) {
        List<Task> tasks = taskService.getTasksByDateAscending(createdAt);
        model.addAttribute("tasks", tasks);
        return "tasksList";
    }

    @GetMapping("/{goalName}/date-desc")
    public String getTasksByDateDescending(
            @PathVariable String goalName,
            @RequestParam LocalDate createdAt,
            Model model) {
        List<Task> tasks = taskService.getTasksByDateDescending(createdAt);
        model.addAttribute("tasks", tasks);
        return "tasksList";
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
