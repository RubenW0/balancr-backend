package com.example.balancrapi.service;

import com.example.balancrapi.model.Task;
import com.example.balancrapi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository);
    }

    @Test
    void getAllTasks_returnsAllTasksFromRepository() {
        List<Task> tasks = List.of(new Task("Buy milk", false), new Task("Do laundry", true));
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        assertThat(result).isEqualTo(tasks);
    }

    @Test
    void getTaskById_returnsTask_whenFound() {
        Task task = new Task("Buy milk", false);
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        assertThat(result).isEqualTo(task);
    }

    @Test
    void getTaskById_throwsNotFound_whenMissing() {
        when(taskRepository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTaskById(42L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Task not found: 42");
    }

    @Test
    void createTask_savesAndReturnsTask() {
        Task task = new Task("New task", false);
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(task);

        assertThat(result).isEqualTo(task);
        verify(taskRepository).save(task);
    }

    @Test
    void updateTask_updatesTitleAndCompleted_thenSaves() {
        Task existing = new Task("Old title", false);
        existing.setId(1L);
        Task updates = new Task("New title", true);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task result = taskService.updateTask(1L, updates);

        assertThat(result.getLongTitle()).isEqualTo("New title");
        assertThat(result.isCompleted()).isTrue();
        verify(taskRepository).save(existing);
    }

    @Test
    void updateTask_throwsNotFound_whenTaskMissing() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.updateTask(99L, new Task("x", false)))
                .isInstanceOf(ResponseStatusException.class);

        verify(taskRepository, never()).save(any());
    }

    @Test
    void deleteTask_deletes_whenExists() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        taskService.deleteTask(1L);

        verify(taskRepository).deleteById(1L);
    }

    @Test
    void deleteTask_throwsNotFound_whenMissing() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> taskService.deleteTask(1L))
                .isInstanceOf(ResponseStatusException.class);

        verify(taskRepository, never()).deleteById(anyLong());
    }

    @Test
    void getTasksByCompleted_delegatesToRepository() {
        List<Task> completedTasks = List.of(new Task("Done", true));
        when(taskRepository.findByCompleted(true)).thenReturn(completedTasks);

        List<Task> result = taskService.getTasksByCompleted(true);

        assertThat(result).isEqualTo(completedTasks);
    }
}
