package com.igor.bondezam.ToDoTask.controller.graphql;


import com.igor.bondezam.ToDoTask.domain.Task;
import com.igor.bondezam.ToDoTask.domain.enums.TaskPriority;
import com.igor.bondezam.ToDoTask.dto.TaskDTO;
import com.igor.bondezam.ToDoTask.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@PreAuthorize("isAuthenticated()")
public class TaskGraphQLController {

    @Autowired
    private TaskService taskService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @QueryMapping
    public List<Task> getAllTasks() {
        return taskService.findAllTasks();
    }

    @QueryMapping
    public TaskDTO getTaskById(@Argument Long id) {
        return TaskDTO.fromEntity(taskService.findTaskById(id));
    }

    @QueryMapping
    public List<TaskDTO> getTasksByStatus(@Argument Boolean completed) {
        return TaskDTO.fromEntityList(taskService.findTasksByCompleted(completed));
    }

    @QueryMapping
    public List<TaskDTO> getTasksByPriority(@Argument TaskPriority priority) {
        return TaskDTO.fromEntityList(taskService.findTasksByPriority(priority));
    }

    @QueryMapping
    public List<TaskDTO> getOverdueTasks() {
        return TaskDTO.fromEntityList(taskService.findOverdueTasks());
    }

    @QueryMapping
    public List<TaskDTO> getDueDateBefore(@Argument LocalDateTime date) {
        return TaskDTO.fromEntityList(taskService.findByDueDateBefore(date));
    }

    @QueryMapping
    public List<TaskDTO> getPendingTasks() {
        return TaskDTO.fromEntityList(taskService.findPendingTasksOrderByPriorityAndDueDate());
    }

    @QueryMapping
    public List<TaskDTO> searchTasksByTitle(@Argument String title) {
        return TaskDTO.fromEntityList(taskService.searchTasksByTitle(title));
    }

    @MutationMapping
    public TaskDTO createTask(@Argument TaskInput task) {
        Task newTask = new Task();
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setCompleted(Optional.ofNullable(task.getCompleted()).orElse(false));
        newTask.setPriority((TaskPriority) Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM));

        if (task.getDueDate() != null) {
            newTask.setDueDate(LocalDateTime.parse(task.getDueDate(), DATE_FORMATTER));
        }

        return TaskDTO.fromEntity(taskService.createTask(newTask));
    }

    @MutationMapping
    public TaskDTO updateTask(@Argument Long id, @Argument TaskUpdateInput task) {
        Task existingTask = taskService.findTaskById(id);

        if (task.getTitle() != null) {
            existingTask.setTitle(task.getTitle());
        }

        if (task.getDescription() != null) {
            existingTask.setDescription(task.getDescription());
        }

        if (task.getCompleted() != null) {
            existingTask.setCompleted(task.getCompleted());
        }

        if (task.getPriority() != null) {
            existingTask.setPriority(task.getPriority());
        }

        if (task.getDueDate() != null) {
            existingTask.setDueDate(LocalDateTime.parse(task.getDueDate(), DATE_FORMATTER));
        }

        return TaskDTO.fromEntity(taskService.updateTask(id, existingTask));
    }

    @MutationMapping
    public TaskDTO toggleTaskCompletion(@Argument Long id) {
        return TaskDTO.fromEntity(taskService.toggleTaskCompletion(id));
    }

    @MutationMapping
    public Boolean deleteTask(@Argument Long id) {
        taskService.deleteTask(id);
        return true;
    }
}
