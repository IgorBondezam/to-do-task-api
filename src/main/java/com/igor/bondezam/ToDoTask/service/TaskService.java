package com.igor.bondezam.ToDoTask.service;

import com.igor.bondezam.ToDoTask.context.UserContextHolder;
import com.igor.bondezam.ToDoTask.domain.Task;
import com.igor.bondezam.ToDoTask.domain.enums.TaskPriority;
import com.igor.bondezam.ToDoTask.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com o ID: " + id));
    }

    public List<Task> findTasksByCompleted(boolean completed) {
        return taskRepository.findByCompleted(completed);
    }

    public List<Task> findTasksByPriority(TaskPriority priority) {
        return taskRepository.findByPriority(priority);
    }

    public List<Task> findOverdueTasks() {
        return taskRepository.findOverdueTasks();
    }

    public List<Task> findByDueDateBefore(LocalDateTime date) {
        return taskRepository.findByDueDateBefore(date);
    }

    public List<Task> findPendingTasksOrderByPriorityAndDueDate() {
        return taskRepository.findPendingTasksOrderByPriorityAndDueDate();
    }

    public List<Task> searchTasksByTitle(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }

    @Transactional
    public Task createTask(Task task) {
        task.setCompleted(false);
        task.setCreatedAt(LocalDateTime.now());

        task.setUser(UserContextHolder.getUser());

        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Long id, Task taskDetails) {
        Task task = findTaskById(id);
        
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());
        task.setPriority(taskDetails.getPriority());
        task.setDueDate(taskDetails.getDueDate());
        
        return taskRepository.save(task);
    }

    @Transactional
    public Task toggleTaskCompletion(Long id) {
        Task task = findTaskById(id);
        task.setCompleted(!task.isCompleted());
        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = findTaskById(id);
        taskRepository.delete(task);
    }
}
