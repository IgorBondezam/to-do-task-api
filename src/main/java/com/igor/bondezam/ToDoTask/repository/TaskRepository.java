package com.igor.bondezam.ToDoTask.repository;

import com.igor.bondezam.ToDoTask.domain.Task;
import com.igor.bondezam.ToDoTask.domain.enums.TaskPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByCompleted(boolean completed);
    
    List<Task> findByPriority(TaskPriority priority);
    
    List<Task> findByDueDateBefore(LocalDateTime date);
    
    List<Task> findByTitleContainingIgnoreCase(String title);
    
    @Query("SELECT t FROM Task t WHERE t.completed = false AND t.dueDate < CURRENT_TIMESTAMP")
    List<Task> findOverdueTasks();
    
    @Query("SELECT t FROM Task t WHERE t.completed = false ORDER BY " +
           "CASE WHEN t.priority = 'HIGH' THEN 0 " +
           "WHEN t.priority = 'MEDIUM' THEN 1 " +
           "ELSE 2 END, " +
           "t.dueDate ASC NULLS LAST")
    List<Task> findPendingTasksOrderByPriorityAndDueDate();
}
