package com.igor.bondezam.ToDoTask.controller;

import com.igor.bondezam.ToDoTask.domain.Task;
import com.igor.bondezam.ToDoTask.domain.enums.TaskPriority;
import com.igor.bondezam.ToDoTask.dto.TaskDTO;
import com.igor.bondezam.ToDoTask.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<Task> tasks = taskService.findAllTasks();
        return ResponseEntity.ok(TaskDTO.fromEntityList(tasks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Task task = taskService.findTaskById(id);
        return ResponseEntity.ok(TaskDTO.fromEntity(task));
    }

    @GetMapping("/status")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(@RequestParam boolean completed) {
        List<Task> tasks = taskService.findTasksByCompleted(completed);
        return ResponseEntity.ok(TaskDTO.fromEntityList(tasks));
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskDTO>> getTasksByPriority(@PathVariable TaskPriority priority) {
        List<Task> tasks = taskService.findTasksByPriority(priority);
        return ResponseEntity.ok(TaskDTO.fromEntityList(tasks));
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<TaskDTO>> getOverdueTasks() {
        List<Task> tasks = taskService.findOverdueTasks();
        return ResponseEntity.ok(TaskDTO.fromEntityList(tasks));
    }

    @GetMapping("/due/datebefore")
    public ResponseEntity<List<TaskDTO>> getDueDateBefore(@RequestParam LocalDateTime date) {
        List<Task> tasks = taskService.findByDueDateBefore(date);
        return ResponseEntity.ok(TaskDTO.fromEntityList(tasks));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<TaskDTO>> getPendingTasksOrderByPriorityAndDueDate() {
        List<Task> tasks = taskService.findPendingTasksOrderByPriorityAndDueDate();
        return ResponseEntity.ok(TaskDTO.fromEntityList(tasks));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskDTO>> searchTasksByTitle(@RequestParam String title) {
        List<Task> tasks = taskService.searchTasksByTitle(title);
        return ResponseEntity.ok(TaskDTO.fromEntityList(tasks));
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        Task task = taskDTO.toEntity();
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(TaskDTO.fromEntity(createdTask));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO) {
        Task existingTask = taskService.findTaskById(id);
        Task updatedTask = taskService.updateTask(id, taskDTO.updateEntity(existingTask));
        return ResponseEntity.ok(TaskDTO.fromEntity(updatedTask));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<TaskDTO> toggleTaskCompletion(@PathVariable Long id) {
        Task task = taskService.toggleTaskCompletion(id);
        return ResponseEntity.ok(TaskDTO.fromEntity(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
