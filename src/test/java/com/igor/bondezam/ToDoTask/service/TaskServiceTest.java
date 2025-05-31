package com.igor.bondezam.ToDoTask.service;

import com.igor.bondezam.ToDoTask.domain.Task;
import com.igor.bondezam.ToDoTask.domain.User;
import com.igor.bondezam.ToDoTask.domain.enums.TaskPriority;
import com.igor.bondezam.ToDoTask.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @InjectMocks
    private TaskService taskService;

    private Task task1;
    private Task task2;
    private User testUser;
    private LocalDateTime now;

    static class UserContextHolder {
        public static User getUser() {
            throw new UnsupportedOperationException("Método estático não mockado fora do contexto de teste");
        }
    }

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("steve.jobs@apple.com");
        testUser.setPassword("apple123");
        testUser.setName("testuser");

        task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Test Task 1");
        task1.setDescription("Description 1");
        task1.setCompleted(false);
        task1.setPriority(TaskPriority.MEDIUM);
        task1.setDueDate(now.plusDays(1));
        task1.setUser(testUser);
        task1.setCreatedAt(now.minusDays(1));

        task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Test Task 2");
        task2.setDescription("Description 2");
        task2.setCompleted(true);
        task2.setPriority(TaskPriority.HIGH);
        task2.setDueDate(now.plusDays(2));
        task2.setUser(testUser);
        task2.setCreatedAt(now.minusDays(2));
    }

    @Test
    void findAllTasks_shouldReturnAllTasks() {

        List<Task> expectedTasks = Arrays.asList(task1, task2);
        when(taskRepository.findAll()).thenReturn(expectedTasks);

        List<Task> actualTasks = taskService.findAllTasks();

        assertNotNull(actualTasks);
        assertEquals(2, actualTasks.size());
        assertEquals(expectedTasks, actualTasks);
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void findTaskById_whenTaskExists_shouldReturnTask() {

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));


        Task actualTask = taskService.findTaskById(1L);


        assertNotNull(actualTask);
        assertEquals(task1.getId(), actualTask.getId());
        assertEquals(task1.getTitle(), actualTask.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void findTaskById_whenTaskDoesNotExist_shouldThrowEntityNotFoundException() {

        Long nonExistentId = 99L;
        when(taskRepository.findById(nonExistentId)).thenReturn(Optional.empty());


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            taskService.findTaskById(nonExistentId);
        });

        assertEquals("Tarefa não encontrada com o ID: " + nonExistentId, exception.getMessage());
        verify(taskRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void findTasksByCompleted_shouldReturnMatchingTasks() {

        List<Task> completedTasks = List.of(task2);
        when(taskRepository.findByCompleted(true)).thenReturn(completedTasks);

        List<Task> actualTasks = taskService.findTasksByCompleted(true);

        assertNotNull(actualTasks);
        assertEquals(1, actualTasks.size());
        assertEquals(task2, actualTasks.get(0));
        verify(taskRepository, times(1)).findByCompleted(true);
    }

    @Test
    void findTasksByPriority_shouldReturnMatchingTasks() {

        List<Task> highPriorityTasks = List.of(task2);
        when(taskRepository.findByPriority(TaskPriority.HIGH)).thenReturn(highPriorityTasks);

        List<Task> actualTasks = taskService.findTasksByPriority(TaskPriority.HIGH);

        assertNotNull(actualTasks);
        assertEquals(1, actualTasks.size());
        assertEquals(task2, actualTasks.get(0));
        verify(taskRepository, times(1)).findByPriority(TaskPriority.HIGH);
    }

    @Test
    void findOverdueTasks_shouldReturnOverdueTasks() {

        Task overdueTask = new Task();
        overdueTask.setId(3L);
        overdueTask.setDueDate(now.minusDays(1));
        List<Task> overdueTasks = List.of(overdueTask);
        when(taskRepository.findOverdueTasks()).thenReturn(overdueTasks);

        List<Task> actualTasks = taskService.findOverdueTasks();

        assertNotNull(actualTasks);
        assertEquals(1, actualTasks.size());
        assertEquals(overdueTask, actualTasks.get(0));
        verify(taskRepository, times(1)).findOverdueTasks();
    }

    @Test
    void findByDueDateBefore_shouldReturnMatchingTasks() {

        LocalDateTime specificDate = now.plusDays(3);
        List<Task> tasksBeforeDate = Arrays.asList(task1, task2);
        when(taskRepository.findByDueDateBefore(specificDate)).thenReturn(tasksBeforeDate);

        List<Task> actualTasks = taskService.findByDueDateBefore(specificDate);

        assertNotNull(actualTasks);
        assertEquals(2, actualTasks.size());
        assertEquals(tasksBeforeDate, actualTasks);
        verify(taskRepository, times(1)).findByDueDateBefore(specificDate);
    }

    @Test
    void findPendingTasksOrderByPriorityAndDueDate_shouldReturnOrderedTasks() {

        List<Task> pendingOrderedTasks = List.of(task1);
        when(taskRepository.findPendingTasksOrderByPriorityAndDueDate()).thenReturn(pendingOrderedTasks);

        List<Task> actualTasks = taskService.findPendingTasksOrderByPriorityAndDueDate();

        assertNotNull(actualTasks);
        assertEquals(1, actualTasks.size());
        assertEquals(task1, actualTasks.get(0));
        verify(taskRepository, times(1)).findPendingTasksOrderByPriorityAndDueDate();
    }

    @Test
    void searchTasksByTitle_shouldReturnMatchingTasks() {

        String searchTerm = "Task 1";
        List<Task> foundTasks = List.of(task1);
        when(taskRepository.findByTitleContainingIgnoreCase(searchTerm)).thenReturn(foundTasks);

        List<Task> actualTasks = taskService.searchTasksByTitle(searchTerm);

        assertNotNull(actualTasks);
        assertEquals(1, actualTasks.size());
        assertEquals(task1, actualTasks.get(0));
        verify(taskRepository, times(1)).findByTitleContainingIgnoreCase(searchTerm);
    }

    @Test
    void createTask_shouldSetDefaultsAndSaveTask() {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(testUser, null, testUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Task newTask = new Task();
        newTask.setTitle("New Task");
        newTask.setDescription("New Description");
        newTask.setPriority(TaskPriority.LOW);
        newTask.setDueDate(now.plusDays(5));

        Task savedTask = new Task();
        savedTask.setId(3L);
        savedTask.setTitle(newTask.getTitle());
        savedTask.setDescription(newTask.getDescription());
        savedTask.setPriority(newTask.getPriority());
        savedTask.setDueDate(newTask.getDueDate());
        savedTask.setCompleted(false);
        savedTask.setUser(testUser);

        MockedStatic<UserContextHolder> mockedStatic = Mockito.mockStatic(UserContextHolder.class);
        mockedStatic.when(UserContextHolder::getUser).thenReturn(testUser);


        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task taskToSave = invocation.getArgument(0);

            assertFalse(taskToSave.isCompleted());
            assertNotNull(taskToSave.getCreatedAt());
            assertTrue(taskToSave.getCreatedAt().isAfter(now.minusSeconds(5)) && taskToSave.getCreatedAt().isBefore(now.plusSeconds(5)));
            assertEquals(testUser, taskToSave.getUser());

            savedTask.setId(3L);
            savedTask.setCreatedAt(taskToSave.getCreatedAt());
            return savedTask;
        });

            Task resultTask = taskService.createTask(newTask);

            assertNotNull(resultTask);
            assertEquals(savedTask.getId(), resultTask.getId());
            assertEquals(savedTask.getTitle(), resultTask.getTitle());
            assertFalse(resultTask.isCompleted());
            assertNotNull(resultTask.getCreatedAt());
            assertEquals(testUser, resultTask.getUser());

            verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void updateTask_whenTaskExists_shouldUpdateAndSaveChanges() {

        Long taskId = 1L;
        Task taskDetails = new Task();
        taskDetails.setTitle("Updated Title");
        taskDetails.setDescription("Updated Description");
        taskDetails.setCompleted(true);
        taskDetails.setPriority(TaskPriority.HIGH);
        taskDetails.setDueDate(now.plusDays(10));

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task1));

        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Task updatedTask = taskService.updateTask(taskId, taskDetails);

        assertNotNull(updatedTask);
        assertEquals(taskId, updatedTask.getId());
        assertEquals(taskDetails.getTitle(), updatedTask.getTitle());
        assertEquals(taskDetails.getDescription(), updatedTask.getDescription());
        assertEquals(taskDetails.isCompleted(), updatedTask.isCompleted());
        assertEquals(taskDetails.getPriority(), updatedTask.getPriority());
        assertEquals(taskDetails.getDueDate(), updatedTask.getDueDate());

        assertEquals(task1.getUser(), updatedTask.getUser());
        assertEquals(task1.getCreatedAt(), updatedTask.getCreatedAt());

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(task1);
    }

    @Test
    void updateTask_whenTaskDoesNotExist_shouldThrowEntityNotFoundException() {

        Long nonExistentId = 99L;
        Task taskDetails = new Task();
        when(taskRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            taskService.updateTask(nonExistentId, taskDetails);
        });

        assertEquals("Tarefa não encontrada com o ID: " + nonExistentId, exception.getMessage());
        verify(taskRepository, times(1)).findById(nonExistentId);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void toggleTaskCompletion_whenTaskExists_shouldToggleAndSave() {

        Long taskId = 1L;
        boolean initialCompletedStatus = task1.isCompleted();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task1));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task toggledTask = taskService.toggleTaskCompletion(taskId);

        assertNotNull(toggledTask);
        assertEquals(taskId, toggledTask.getId());
        assertEquals(!initialCompletedStatus, toggledTask.isCompleted());

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(task1);
        assertEquals(!initialCompletedStatus, task1.isCompleted());
    }

    @Test
    void toggleTaskCompletion_whenTaskDoesNotExist_shouldThrowEntityNotFoundException() {

        Long nonExistentId = 99L;
        when(taskRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            taskService.toggleTaskCompletion(nonExistentId);
        });

        assertEquals("Tarefa não encontrada com o ID: " + nonExistentId, exception.getMessage());
        verify(taskRepository, times(1)).findById(nonExistentId);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void deleteTask_whenTaskExists_shouldCallRepositoryDelete() {

        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task1));

        doNothing().when(taskRepository).delete(task1);

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).delete(task1);
    }

    @Test
    void deleteTask_whenTaskDoesNotExist_shouldThrowEntityNotFoundException() {

        Long nonExistentId = 99L;
        when(taskRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            taskService.deleteTask(nonExistentId);
        });

        assertEquals("Tarefa não encontrada com o ID: " + nonExistentId, exception.getMessage());
        verify(taskRepository, times(1)).findById(nonExistentId);
        verify(taskRepository, never()).delete(any(Task.class));
    }
}