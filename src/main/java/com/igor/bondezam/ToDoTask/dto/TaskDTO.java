package com.igor.bondezam.ToDoTask.dto;

import com.igor.bondezam.ToDoTask.domain.Task;
import com.igor.bondezam.ToDoTask.domain.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TaskDTO {
    private Long id;
    
    @NotBlank(message = "O título da tarefa é obrigatório")
    private String title;
    
    private String description;
    
    private boolean completed;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private LocalDateTime dueDate;
    
    private TaskPriority priority;
    
    /**
     * Converte uma entidade Task para TaskDTO
     * @param task entidade a ser convertida
     * @return TaskDTO correspondente
     */
    public static TaskDTO fromEntity(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getDueDate(),
                task.getPriority());
    }

    public static List<TaskDTO> fromEntityList(List<Task> tasks) {
        return tasks.stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Task toEntity() {
        Task task = new Task();
        task.setId(this.id);
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setCompleted(this.completed);
        task.setDueDate(this.dueDate);
        task.setPriority(this.priority != null ? this.priority : TaskPriority.MEDIUM);
        return task;
    }

    public Task updateEntity(Task existingTask) {
        existingTask.setTitle(this.title);
        existingTask.setDescription(this.description);
        existingTask.setCompleted(this.completed);
        existingTask.setDueDate(this.dueDate);
        existingTask.setPriority(this.priority != null ? this.priority : existingTask.getPriority());
        return existingTask;
    }

    public TaskDTO() {
    }

    public TaskDTO(Long id, String title, String description, boolean completed, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime dueDate, TaskPriority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
}
